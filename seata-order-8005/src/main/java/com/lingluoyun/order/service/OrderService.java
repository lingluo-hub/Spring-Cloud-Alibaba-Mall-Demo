package com.lingluoyun.order.service;

import com.lingluoyun.order.entity.CommonResult;
import com.lingluoyun.order.entity.Order;

public interface OrderService {

    /**
     * 创建订单数据
     * @param order
     */
    CommonResult create(Order order);
}
