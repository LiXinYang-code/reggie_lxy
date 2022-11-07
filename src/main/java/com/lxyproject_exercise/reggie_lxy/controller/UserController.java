package com.lxyproject_exercise.reggie_lxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lxyproject_exercise.reggie_lxy.common.R;
import com.lxyproject_exercise.reggie_lxy.entity.User;
import com.lxyproject_exercise.reggie_lxy.service.UserService;
import com.lxyproject_exercise.reggie_lxy.utils.SMSUtils;
import com.lxyproject_exercise.reggie_lxy.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user ,HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if (phone!=null){
            //生成随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code = {}",code);

            //调用阿里云短讯服务API发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //将生成的验证码进行保存
            session.setAttribute(phone,code);
            return R.success("短信发送成功");
        }

        return R.error("短信发送失败");
    }


    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map , HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中获取保存的验证码
        Object codeInSession =  session.getAttribute(phone);

        //进行验证码的比对
        if (codeInSession != null && codeInSession.equals(code)){
            //比对成功，说明登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if (user==null){
                //判断手机号对应的用户是否为新用户，如果是新用户，则自动注册用户信息
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");

    }


}
