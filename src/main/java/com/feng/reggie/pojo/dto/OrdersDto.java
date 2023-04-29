package com.feng.reggie.pojo.dto;

import com.feng.reggie.pojo.po.OrderDetail;
import com.feng.reggie.pojo.po.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author f
 * @date 2023/4/29 12:18
 */
@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
}
