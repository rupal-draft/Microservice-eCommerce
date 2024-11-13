package com.microservice.eccomerce.order_service.service;

import com.microservice.eccomerce.order_service.dto.OrderRequestDto;

import java.util.List;

public interface OrderService {
    List<OrderRequestDto> getAllOrders();
    OrderRequestDto getOrderById(Long id);
}
