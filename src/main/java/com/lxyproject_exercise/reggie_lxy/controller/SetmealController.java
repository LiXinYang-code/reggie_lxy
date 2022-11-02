package com.lxyproject_exercise.reggie_lxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lxyproject_exercise.reggie_lxy.common.R;
import com.lxyproject_exercise.reggie_lxy.dto.SetmealDto;
import com.lxyproject_exercise.reggie_lxy.entity.Category;
import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.entity.SetmealDish;
import com.lxyproject_exercise.reggie_lxy.service.CategoryService;
import com.lxyproject_exercise.reggie_lxy.service.SetmealDishService;
import com.lxyproject_exercise.reggie_lxy.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name!=null,Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝,因为record泛型不一样，所以不用去拷贝
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list  = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

}
