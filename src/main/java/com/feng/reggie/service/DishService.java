package com.feng.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.reggie.pojo.dto.DishDto;
import com.feng.reggie.pojo.po.Dish;

/**
 * @author f
 * @date 2023/4/27 23:15
 */
public interface DishService extends IService<Dish> {

    /**
     * save
     * @param dishDto dto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * get
     * @param id id
     * @return   res
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * update
     * @param dishDto dishDto
     */
    void updateWithFlavor(DishDto dishDto);
}
