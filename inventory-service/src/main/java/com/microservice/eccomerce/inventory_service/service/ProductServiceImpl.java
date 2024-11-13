package com.microservice.eccomerce.inventory_service.service;

import com.microservice.eccomerce.inventory_service.dto.ProductDto;
import com.microservice.eccomerce.inventory_service.entity.Product;
import com.microservice.eccomerce.inventory_service.exceptions.ResourceNotFound;
import com.microservice.eccomerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllInventory() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        return products
                .stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.info("Getting the product");
        Product product = productRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFound("No product found"));
        return modelMapper.map(product,ProductDto.class);
    }
}
