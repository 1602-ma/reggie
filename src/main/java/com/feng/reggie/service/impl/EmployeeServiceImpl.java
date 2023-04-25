package com.feng.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.reggie.dao.EmployeeMapper;
import com.feng.reggie.pojo.po.Employee;
import com.feng.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author f
 * @date 2023/4/25 21:56
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
