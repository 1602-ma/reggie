package com.feng.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.feng.reggie.common.Response.R;
import com.feng.reggie.pojo.po.Employee;
import com.feng.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

}
