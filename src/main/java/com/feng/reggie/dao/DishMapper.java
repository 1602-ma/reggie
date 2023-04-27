package com.feng.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.reggie.pojo.po.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author f
 * @date 2023/4/27 23:15
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}
