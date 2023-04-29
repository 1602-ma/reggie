package com.feng.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.reggie.pojo.dto.SetMealDto;
import com.feng.reggie.pojo.po.SetMeal;
import com.feng.reggie.pojo.po.SetMealDish;

/**
 * @author f
 * @date 2023/4/27 23:16
 */
public interface SetMealService extends IService<SetMeal> {

    /**
     * save
     * @param setMealDto dto
     */
    void saveWithDish(SetMealDto setMealDto);

    /**
     * get
     * @param id id
     * @return   dto
     */
    SetMealDto getByIdWithDish(Long id);

    /**
     * update
     * @param setMealDto dto
     */
    void updateWithDish(SetMealDto setMealDto);
}
