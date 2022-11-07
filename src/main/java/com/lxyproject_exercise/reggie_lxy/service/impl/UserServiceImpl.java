package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.entity.User;
import com.lxyproject_exercise.reggie_lxy.mapper.UserMapper;
import com.lxyproject_exercise.reggie_lxy.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
