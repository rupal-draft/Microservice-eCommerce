package com.microservice.eccomerce.inventory_service.service;

import com.microservice.eccomerce.inventory_service.dto.OrderRequestDto;
import com.microservice.eccomerce.inventory_service.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllInventory();
    ProductDto getProductById(Long id);
    Double reduceStock(OrderRequestDto orderRequestDto);
}
