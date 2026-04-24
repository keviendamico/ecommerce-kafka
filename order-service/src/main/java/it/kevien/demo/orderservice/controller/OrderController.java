package it.kevien.demo.orderservice.controller;

import it.kevien.demo.orderservice.model.dto.CreateOrderRequest;
import it.kevien.demo.orderservice.model.dto.CreateOrderResponse;
import it.kevien.demo.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest request) {
        log.info("[ORDER] POST /api/v1/orders - customerId={}", request.customerId());
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.accepted().body(response);
    }
}
