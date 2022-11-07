package com.lxyproject_exercise.reggie_lxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxyproject_exercise.reggie_lxy.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}