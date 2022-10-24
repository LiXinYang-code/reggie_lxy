package com.lxyproject_exercise.reggie_lxy.controller;


import com.lxyproject_exercise.reggie_lxy.common.R;
import com.lxyproject_exercise.reggie_lxy.entity.Employee;
import com.lxyproject_exercise.reggie_lxy.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

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

        return null;
    }

}
