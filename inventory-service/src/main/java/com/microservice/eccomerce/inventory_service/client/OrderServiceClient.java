package com.microservice.eccomerce.inventory_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service",path = "/orders")
public interface OrderServiceClient {

    @GetMapping("/core/inventory/send-orders")
    String getOrders();
}
