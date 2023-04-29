package com.feng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feng.reggie.common.Response.R;
import com.feng.reggie.pojo.po.Employee;
import com.feng.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

/**
 * @author f
 * @date 2023/4/25 21:57
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 登录
     * @param request  request
     * @param employee employee
     * @return         res
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1.将页面提交的密码进行md5加密
        String password = employee.getPassword();
        String passwordWithMD5 = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2.根据页面提交的用户名来查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != employee.getUsername(), Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3.如果没有查询到用户则返回失败结果
        if (null == emp) {
            return R.error("登录失败");
        }

        // 4.对比密码，如果不一致则返回结果
        if (!emp.getPassword().equals(passwordWithMD5)) {
            return R.error("密码错误");
        }

        // 5.查看员工状态，如果已禁用，则返回已禁用结果
        if (0 == emp.getStatus()) {
            return R.error("账号已禁用");
        }

        // 6.登录成功，将用户id存入Session并返回成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理Session中保存的当前员工登录的id
        request.getSession().removeAttribute("employee");
        return R.success("推出成功");
    }

    /**
     * 新增员工
     * @param request   request
     * @param employee  emp
     * @return          res
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工信息：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 分页查询员工信息
     * @param page     page
     * @param pageSize  pageSize
     * @param name     name
     * @return         page
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page{}, pageSize{},name={}", page, pageSize, name);

        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name)
                .orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, wrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        Long empId = (Long)request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id) {
        log.info("根据id查：{}", id);
        Employee emp = employeeService.getById(id);
        if (null != emp) {
            return R.success(emp);
        }

        return R.error("未查到该用户");
    }

}
