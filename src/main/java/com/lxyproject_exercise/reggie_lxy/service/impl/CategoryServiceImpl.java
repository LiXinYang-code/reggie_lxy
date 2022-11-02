package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.common.CustomException;
import com.lxyproject_exercise.reggie_lxy.entity.Category;
import com.lxyproject_exercise.reggie_lxy.entity.Dish;
import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.mapper.CategoryMapper;
import com.lxyproject_exercise.reggie_lxy.service.CategoryService;
import com.lxyproject_exercise.reggie_lxy.service.DishService;
import com.lxyproject_exercise.reggie_lxy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private  DishService dishServicel;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要进行判断是否属于套餐或关联了菜品
     * @param id
     */
    @Override
    public void remove(Long id){
        //查询是否属于菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishServicel.count(dishLambdaQueryWrapper);
        if (count1>0){
            //抛出异常，已关联菜品
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        //查询是否属于套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0){
            //抛出异常，已关联套餐
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        //正常删除
        super.removeById(id);

    }
}
