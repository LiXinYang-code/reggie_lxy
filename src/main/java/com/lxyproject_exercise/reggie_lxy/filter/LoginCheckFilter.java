package com.lxyproject_exercise.reggie_lxy.filter;
import com.alibaba.fastjson.JSON;
import com.lxyproject_exercise.reggie_lxy.common.BaseContext;
import com.lxyproject_exercise.reggie_lxy.common.R;
import javafx.util.BuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import  javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 设置过滤器，检查用户是否完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //创建路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取此次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求：{}",requestURI);

        //定义不需要请求的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //判断本次请求是否需要处理
        boolean check = check(urls,requestURI);

        //如果不需要处理，直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //需要判断登录状态，如果已登陆，则直接放行
        if (request.getSession().getAttribute("employee")!= null){
            log.info("用户已登陆,用户id为: {}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setThreadLocal(empId);

            filterChain.doFilter(request,response);
            return;
        }

        //判断移动端登陆状态吗，如果已登陆，则直接放行
        if (request.getSession().getAttribute("user")!= null){
            log.info("用户已登陆,用户id为: {}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setThreadLocal(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //如果未登录则返回未登陆结果，通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }


}
