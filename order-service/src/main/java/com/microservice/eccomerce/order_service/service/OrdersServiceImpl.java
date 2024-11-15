package com.microservice.eccomerce.order_service.service;

import com.microservice.eccomerce.order_service.client.InventoryServiceClient;
import com.microservice.eccomerce.order_service.dto.OrderRequestDto;
import com.microservice.eccomerce.order_service.dto.OrderRequestItemDto;
import com.microservice.eccomerce.order_service.entities.OrderItem;
import com.microservice.eccomerce.order_service.entities.OrderStatus;
import com.microservice.eccomerce.order_service.entities.Orders;
import com.microservice.eccomerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrderService{

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final InventoryServiceClient inventoryServiceClient;

    @Override
    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }
    @Override
    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }

    @Override
    @Retry(name = "inventoryRetry", fallbackMethod = "fallBackMethodReduceOrder")
    @RateLimiter(name = "inventoryRateLimiter" , fallbackMethod = "fallBackMethodReduceOrder")
    @CircuitBreaker(name = "inventoryCircuitBreaker",fallbackMethod = "fallBackMethodReduceOrder")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {

        log.info("Creating order!!");
        Double price = inventoryServiceClient.reduceStock(orderRequestDto);
        Orders orders = modelMapper.map(orderRequestDto,Orders.class);
        for(OrderItem orderRequestItem : orders.getItems()){
            orderRequestItem.setOrder(orders);
        }
        orders.setPrice(price);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrder = orderRepository.save(orders);
        return modelMapper.map(savedOrder,OrderRequestDto.class);
    }

    public OrderRequestDto fallBackMethodReduceOrder(OrderRequestDto orderRequestDto,Throwable throwable){
        log.error(throwable.getLocalizedMessage());
        return new OrderRequestDto();
    }
}
