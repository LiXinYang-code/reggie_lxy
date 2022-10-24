package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.entity.Employee;
import com.lxyproject_exercise.reggie_lxy.mapper.EmployeeMapper;
import com.lxyproject_exercise.reggie_lxy.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
