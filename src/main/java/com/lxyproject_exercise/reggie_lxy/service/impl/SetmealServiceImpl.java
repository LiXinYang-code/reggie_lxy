package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.dto.SetmealDto;
import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.entity.SetmealDish;
import com.lxyproject_exercise.reggie_lxy.mapper.SetmealMapper;
import com.lxyproject_exercise.reggie_lxy.service.SetmealDishService;
import com.lxyproject_exercise.reggie_lxy.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐,保存套餐和菜品的关联关系
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){
        //保存套餐基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品关联信息
        setmealDishService.saveBatch(setmealDishes);
    }
}
