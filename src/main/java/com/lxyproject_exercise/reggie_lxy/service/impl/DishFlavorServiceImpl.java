package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.entity.DishFlavor;
import com.lxyproject_exercise.reggie_lxy.mapper.DishFlavorMapper;
import com.lxyproject_exercise.reggie_lxy.service.DishFlavorService;
import com.lxyproject_exercise.reggie_lxy.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
