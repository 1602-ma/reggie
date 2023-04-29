package com.feng.reggie.pojo.dto;

import com.feng.reggie.pojo.po.SetMeal;
import com.feng.reggie.pojo.po.SetMealDish;
import lombok.Data;

import java.util.List;

/**
 * @author f
 * @date 2023/4/29 12:29
 */
@Data
public class SetMealDto extends SetMeal {

    private List<SetMealDish> setmealDishes;

    private String categoryName;
}
