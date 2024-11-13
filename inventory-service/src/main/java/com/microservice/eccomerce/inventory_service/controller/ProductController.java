package com.microservice.eccomerce.inventory_service.controller;

import com.microservice.eccomerce.inventory_service.dto.ProductDto;
import com.microservice.eccomerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class ProductController {

    private final ProductService productService;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/fetch-orders")
    public String getOrders(){
        ServiceInstance serviceInstance = discoveryClient
                .getInstances("order-service")
                .getFirst();

        return restClient
                .get()
                .uri(serviceInstance.getUri()+"/api/v1/orders/inventory/send-orders")
                .retrieve()
                .body(String.class);
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
}
