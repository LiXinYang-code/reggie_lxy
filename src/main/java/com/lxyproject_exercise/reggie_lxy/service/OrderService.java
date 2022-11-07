package com.lxyproject_exercise.reggie_lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxyproject_exercise.reggie_lxy.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);
}
