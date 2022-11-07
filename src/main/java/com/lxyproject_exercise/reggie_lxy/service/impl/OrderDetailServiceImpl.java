package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.entity.OrderDetail;
import com.lxyproject_exercise.reggie_lxy.mapper.OrderDetailMapper;
import com.lxyproject_exercise.reggie_lxy.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}