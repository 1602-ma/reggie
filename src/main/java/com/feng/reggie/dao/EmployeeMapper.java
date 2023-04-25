package com.feng.reggie.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feng.reggie.pojo.po.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author f
 * @date 2023/4/25 21:54
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
