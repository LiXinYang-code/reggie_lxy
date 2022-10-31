package com.lxyproject_exercise.reggie_lxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxyproject_exercise.reggie_lxy.common.R;
import com.lxyproject_exercise.reggie_lxy.entity.Employee;
import com.lxyproject_exercise.reggie_lxy.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆
     * @Param request
     * @Param employee
     * @return
     */
    @PostMapping("/login")
    //因为传入的JSON对象，因此需要用RequestBody来接受
    //需要通过request获取session，从而访问emplee的id
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名来查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //查询失败返回登陆失败结果
        if (emp == null){
            return R.error("登陆失败");
        }

        //密码比对，不一致则返回登陆失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //查看员工状态是否为禁用状态，0为禁言，1为可用
        if (emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //登陆成功：将用户id放入session中
        request.getSession().setAttribute("employee",emp.getId());
        return  R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理session中保存的当前登陆员工的id
        request.getSession().removeAttribute("employee");
        return  R.success("成功退出");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    //因为传入的是json对象，因此需要RequestBody来接收
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());

        //设置初始密码为123456，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //employee.setCreateTime(LocalDateTime.now());
       // employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的ID
       // Long empId = (Long) request.getSession().getAttribute("employee");
        // employee.setCreateUser(empId);
       //  employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");

    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        //分页构造器
        Page pageInfo = new Page(page,pageSize);

        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤
        queryWrapper.like(name!=null,Employee::getName,name);
        //排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据ID修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
      //  Long empId = (Long) request.getSession().getAttribute("employee");
      //  employee.setUpdateTime(LocalDateTime.now());
      //  employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据ID查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){

        log.info("根据ID查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return  R.error("没有查询到对应员工信息");
    }

}
