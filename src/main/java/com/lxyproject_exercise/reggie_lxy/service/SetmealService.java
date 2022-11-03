package com.lxyproject_exercise.reggie_lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxyproject_exercise.reggie_lxy.dto.SetmealDto;
import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐,保存套餐和菜品的关联关系
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐,同时需要删除所关联的菜品数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
