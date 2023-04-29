package com.feng.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.reggie.common.exception.CustomException;
import com.feng.reggie.dao.CategoryMapper;
import com.feng.reggie.pojo.po.Category;
import com.feng.reggie.pojo.po.Dish;
import com.feng.reggie.pojo.po.SetMeal;
import com.feng.reggie.service.CategoryService;
import com.feng.reggie.service.DishService;
import com.feng.reggie.service.SetMealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author f
 * @date 2023/4/27 22:53
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private DishService dishService;

    @Resource
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(lambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("已经关联菜品，无法删除");
        }

        LambdaQueryWrapper<SetMeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(SetMeal::getCategoryId, id);
        int count1 = setMealService.count(lambdaQueryWrapper1);
        if (count1 > 0) {
            throw new CustomException("已经关联套餐，无法删除");
        }
        super.removeById(id);
    }
}
