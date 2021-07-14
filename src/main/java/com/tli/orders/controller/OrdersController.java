package com.tli.orders.controller;

import com.tli.orders.entity.Orders;
import com.tli.orders.request.OrdersRequest;
import com.tli.orders.response.OrdersResponse;
import com.tli.orders.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @GetMapping(value = "/view")
    public ResponseEntity<OrdersResponse> viewOrder(@RequestParam Long orderId) {
        return this.ordersService.viewOrder(orderId);
    }

    @PostMapping(value = "/place")
    public ResponseEntity<OrdersResponse> placeOrder(@RequestBody OrdersRequest ordersRequest) {
        return this.ordersService.placeOrder(ordersRequest);
    }

    @GetMapping(value = "/cancel")
    public ResponseEntity<OrdersResponse> cancelOrder(@RequestParam Long orderId) {
        return this.ordersService.cancelOrder(orderId);
    }

    @PostMapping(value = "/change")
    public ResponseEntity<OrdersResponse> changeQuantity(@RequestBody OrdersRequest ordersRequest) {
        return this.ordersService.changeQuantity(ordersRequest);
    }

    @GetMapping(value = "/remove")
    public ResponseEntity<OrdersResponse> removeLineItem(@RequestParam Long orderId, Integer itemNumber) {
        return this.ordersService.removeLineItem(orderId, itemNumber);
    }
}
