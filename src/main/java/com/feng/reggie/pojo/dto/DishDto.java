package com.feng.reggie.pojo.dto;

import com.feng.reggie.pojo.po.Dish;
import com.feng.reggie.pojo.po.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author f
 * @date 2023/4/28 21:04
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
