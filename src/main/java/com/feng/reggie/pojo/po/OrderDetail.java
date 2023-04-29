package com.feng.reggie.pojo.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author f
 * @date 2023/4/29 12:22
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 名称 */
    private String name;

    /** 订单id */
    private Long orderId;

    /** 菜品id */
    private Long dishId;


    /** 套餐id */
    private Long setMealId;


    /** 口味 */
    private String dishFlavor;


    /** 数量 */
    private Integer number;

    /** 金额 */
    private BigDecimal amount;

    /** 图片 */
    private String image;
}
