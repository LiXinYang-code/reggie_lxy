package com.lxyproject_exercise.reggie_lxy.dto;

import com.lxyproject_exercise.reggie_lxy.entity.Setmeal;
import com.lxyproject_exercise.reggie_lxy.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
