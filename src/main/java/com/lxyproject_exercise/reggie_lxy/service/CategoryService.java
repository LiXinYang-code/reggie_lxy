package com.lxyproject_exercise.reggie_lxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxyproject_exercise.reggie_lxy.entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
