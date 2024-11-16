package com.microservice.eccomerce.inventory_service.controller;

import com.microservice.eccomerce.inventory_service.client.OrderServiceClient;
import com.microservice.eccomerce.inventory_service.dto.OrderRequestDto;
import com.microservice.eccomerce.inventory_service.dto.ProductDto;
import com.microservice.eccomerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@RefreshScope
public class ProductController {

    private final ProductService productService;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;
    private final OrderServiceClient orderServiceClient;

    @GetMapping("/fetch-orders")
    public String getOrders(){
        return orderServiceClient.getOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getInventory(){
        List<ProductDto> products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/reduce-stock")
    public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDto orderRequestDto){
        Double price = productService.reduceStock(orderRequestDto);
        return ResponseEntity.ok(price);
    }
}
