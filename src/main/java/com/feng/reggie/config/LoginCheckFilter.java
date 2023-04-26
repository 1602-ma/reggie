package com.feng.reggie.config;

import com.alibaba.fastjson.JSON;
import com.feng.reggie.common.Response.R;
import com.feng.reggie.pojo.po.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 检查用户是否已经完成登录
 * @author f
 * @date 2023/4/26 21:29
 */
@WebFilter(filterName = "LoginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;
        log.info("拦截到请求：{}", ((HttpServletRequest) request).getRequestURI());
        // 1.获取本次请求的URI
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        // 2. 定义不需要处理的请求路径
        String[] urls = new String[]{
          "/employee/login"
          , "/employee/logout"
          , "/backend/**"
          , "/front/**"
        };

        // 3.如果不需要处理，直接放行
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            chain.doFilter(request, response);
            return;
        }

        // 4.判断登录状态，如果已经登录，则直接放行
        if (((HttpServletRequest) request).getSession().getAttribute("employee") != null) {
            log.info("用户已经登录，id为:{}", ((HttpServletRequest) request).getSession().getAttribute("employee"));
            chain.doFilter(request, response);
            return;
        }

        // 如果未登录则返回未登录结果，通过输出流向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        chain.doFilter(request, response);
    }

    /**
     * 路径匹配，检查本次是否放行
     * @param urls       urls
     * @param requestURI uri
     * @return           boolean
     */
    private boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (true == match) {
                return true;
            }
        }
        return false;
    }
}
