package com.feng.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.reggie.dao.SetMealMapper;
import com.feng.reggie.pojo.dto.SetMealDto;
import com.feng.reggie.pojo.po.SetMeal;
import com.feng.reggie.pojo.po.SetMealDish;
import com.feng.reggie.service.SetMealDishService;
import com.feng.reggie.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author f
 * @date 2023/4/27 23:17
 */
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {

    @Resource
    private SetMealDishService setMealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetMealDto setMealDto) {
        this.save(setMealDto);

        List<SetMealDish> setMealDishes = setMealDto.getSetmealDishes();
        setMealDishes.stream().map(item -> {
            item.setSetmealId(setMealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setMealDishService.saveBatch(setMealDishes);
    }

    @Override
    public SetMealDto getByIdWithDish(Long id) {
        SetMeal setMeal = this.getById(id);
        SetMealDto setMealDto = new SetMealDto();
        BeanUtils.copyProperties(setMeal, setMealDto);

        LambdaQueryWrapper<SetMealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetMealDish::getSetmealId, setMeal.getId());
        List<SetMealDish> list = setMealDishService.list(lambdaQueryWrapper);
        setMealDto.setSetmealDishes(list);
        return setMealDto;
    }

    @Override
    public void updateWithDish(SetMealDto setMealDto) {
        this.updateById(setMealDto);

        LambdaQueryWrapper<SetMealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetMealDish::getSetmealId, setMealDto.getId());
        setMealDishService.remove(lambdaQueryWrapper);

        List<SetMealDish> setmealDishes = setMealDto.getSetmealDishes();
        List<SetMealDish> list = setmealDishes.stream().map(item -> {
            item.setSetmealId(setMealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setMealDishService.saveBatch(list);
    }
}
