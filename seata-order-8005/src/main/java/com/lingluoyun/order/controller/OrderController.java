package com.lingluoyun.order.controller;

import com.lingluoyun.order.entity.CommonResult;
import com.lingluoyun.order.entity.Order;
import com.lingluoyun.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/create/{productId}/{count}/{money}")
    public CommonResult create(@PathVariable("productId") Integer productId, @PathVariable("count") Integer count
            , @PathVariable("money") BigDecimal money) {
        Order order = new Order();
        order.setProductId(Integer.valueOf(productId).longValue());
        order.setCount(count);
        order.setMoney(money);
        return orderService.create(order);
    }

    /**
     * 使用 @GlobalTransactional 注解对分布式事务进行管理
     */
    @GetMapping("/order/createByAnnotation/{productId}/{count}/{money}")
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    public CommonResult createByAnnotation(@PathVariable("productId") Integer productId, @PathVariable("count") Integer count
            , @PathVariable("money") BigDecimal money) {
        Order order = new Order();
        order.setProductId(Integer.valueOf(productId).longValue());
        order.setCount(count);
        order.setMoney(money);
        return orderService.create(order);
    }
}
