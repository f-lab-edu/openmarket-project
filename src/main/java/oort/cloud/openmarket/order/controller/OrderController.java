package oort.cloud.openmarket.order.controller;

import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.order.controller.reponse.OrderCancelResponse;
import oort.cloud.openmarket.order.controller.reponse.OrderCreateResponse;
import oort.cloud.openmarket.order.controller.request.OrderCancelRequest;
import oort.cloud.openmarket.order.controller.request.OrderCreateRequest;
import oort.cloud.openmarket.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/v1/order")
    public ResponseEntity<OrderCreateResponse> createOrder(
            @RequestBody @Valid OrderCreateRequest request,
            @AccessToken AccessTokenPayload user){
        return ResponseEntity
                .ok()
                .body(orderService.createOrder(user.getUserId(), request));
    }

    @PatchMapping("/v1/order/{orderId}/cancel")
    public ResponseEntity<OrderCancelResponse> cancelOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderCancelRequest request
    ){
        return ResponseEntity.ok()
                .body(
                        orderService.cancel(orderId, request)
                );
    }
}
