package com.feng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.reggie.common.Response.R;
import com.feng.reggie.pojo.dto.SetMealDto;
import com.feng.reggie.pojo.po.Category;
import com.feng.reggie.pojo.po.SetMeal;
import com.feng.reggie.service.CategoryService;
import com.feng.reggie.service.SetMealDishService;
import com.feng.reggie.service.SetMealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author f
 * @date 2023/4/29 12:34
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Resource
    private SetMealService setMealService;

    @Resource
    private SetMealDishService setMealDishService;

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody SetMealDto setMealDto) {
        setMealService.saveWithDish(setMealDto);
        return R.success("新增菜单成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<SetMeal> pageInfo = new Page<>(page, pageSize);
        Page<SetMealDto> pageDtoInfo = new Page<>();

        LambdaQueryWrapper<SetMeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), SetMeal::getName, name)
                .orderByDesc(SetMeal::getUpdateTime);
        setMealService.page(pageInfo, lambdaQueryWrapper);

        BeanUtils.copyProperties(pageInfo, pageDtoInfo, "records");
        List<SetMeal> records = pageInfo.getRecords();
        List<SetMealDto> list = records.stream().map(item -> {
            SetMealDto setMealDto = new SetMealDto();
            BeanUtils.copyProperties(item, setMealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (null != category) {
                String categoryName = category.getName();
                setMealDto.setCategoryName(categoryName);
            }
            return setMealDto;
        }).collect(Collectors.toList());

        pageDtoInfo.setRecords(list);
        return R.success(pageDtoInfo);
    }

    @DeleteMapping
    public R<String> delete(String[] ids) {
        int index =0;
        for (String id : ids) {
            SetMeal setMeal = setMealService.getById(id);
            if (1 != setMeal.getStatus()) {
                setMealService.removeById(id);
            }else {
                index++;
            }
        }
        if (index > 0 && ids.length == index) {
            return R.error("选中的套餐均为启售状态，不能删除");
        }else {
            return R.success("删除成功");
        }
    }

    @PostMapping("/status/{status}")
    public R<String> sale(@PathVariable int status, String[] ids) {
        for (String id : ids) {
            SetMeal setMeal = setMealService.getById(id);
            setMeal.setStatus(status);
            setMealService.updateById(setMeal);
        }
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    public R<SetMealDto> getById(@PathVariable Long id) {
        SetMealDto setMealDto = setMealService.getByIdWithDish(id);
        return R.success(setMealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetMealDto setMealDto) {
        setMealService.updateWithDish(setMealDto);
        return R.success("修改成功");
    }
}
