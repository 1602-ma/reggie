package com.feng.reggie.common;

/**
 * 用于保存和获取当前登录用户的id
 * @author f
 * @date 2023/4/27 22:03
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
