package com.lxyproject_exercise.reggie_lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxyproject_exercise.reggie_lxy.dto.SetmealDto;
import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.entity.SetmealDish;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐,保存套餐和菜品的关联关系
     */
    public void saveWithDish(SetmealDto setmealDto);
}
