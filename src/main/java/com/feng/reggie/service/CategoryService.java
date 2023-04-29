package com.feng.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.reggie.pojo.po.Category;

/**
 * @author f
 * @date 2023/4/27 22:53
 */
public interface CategoryService extends IService<Category> {

    /**
     * remove
     * @param id id
     */
    public void remove(Long id);
}
