package com.feng.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.reggie.dao.DishMapper;
import com.feng.reggie.pojo.po.Dish;
import com.feng.reggie.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author f
 * @date 2023/4/27 23:16
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}
