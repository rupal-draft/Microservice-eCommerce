package com.microservice.eccomerce.order_service.client;

import com.microservice.eccomerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryServiceClient {

    @PutMapping("/products/reduce-stock")
    Double reduceStock(OrderRequestDto orderRequestDto);
}
