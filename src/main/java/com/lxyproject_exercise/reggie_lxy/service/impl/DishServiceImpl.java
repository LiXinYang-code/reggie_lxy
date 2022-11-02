package com.lxyproject_exercise.reggie_lxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxyproject_exercise.reggie_lxy.dto.DishDto;
import com.lxyproject_exercise.reggie_lxy.entity.Dish;
import com.lxyproject_exercise.reggie_lxy.entity.DishFlavor;
import com.lxyproject_exercise.reggie_lxy.mapper.DishMapper;
import com.lxyproject_exercise.reggie_lxy.service.DishFlavorService;
import com.lxyproject_exercise.reggie_lxy.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    //新增菜品，同时保存口味数据
    @Transactional
    public  void saveWithFlavor(DishDto dishDto){
        //保存菜品基本信息
        this.save(dishDto);//dishDto继承了dish

        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->{
           item.setDishId(dishId);
           return item;
        }).collect(Collectors.toList());
        //保存菜品口味表
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id){

        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询菜品对应口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表信息
        this.updateById(dishDto);
        //清理当前菜品的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //插入新的提交的数据
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
