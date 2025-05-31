package oort.cloud.openmarket.order.service;

import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.order.controller.reponse.OrderCreateResponse;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.order.entity.OrderItem;
import oort.cloud.openmarket.order.repository.OrderRepository;
import oort.cloud.openmarket.order.controller.request.OrderCreateRequest;
import oort.cloud.openmarket.order.controller.request.OrderItemCreateRequest;
import oort.cloud.openmarket.products.entity.Products;
import oort.cloud.openmarket.products.service.ProductsService;
import oort.cloud.openmarket.user.entity.Address;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductsService productsService;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductsService productsService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productsService = productsService;
    }

    @Transactional
    public OrderCreateResponse createOrder(Long userId, OrderCreateRequest request){
        //userId 유저 조회
        Users user = userService.findUserEntityById(userId);

        //Address 조회
        Long addressId = request.getAddressId();
        Address address = user.findAddress(addressId);

        // 주문 상품 요청 가져오기
        List<OrderItemCreateRequest> orderItemRequests = request.getOrderItemRequests();
        // 주문상품 요청 -> 상품 ID 추출
        List<Long> requestProductIds = orderItemRequests
                .stream().map(OrderItemCreateRequest::getProductId).toList();
        // 상품 조회
        Map<Long, Products> products = productsService.getProductListByIds(requestProductIds)
                .stream().collect(Collectors.toMap(Products::getProductId, Function.identity()));

        // 주문 요청 상품 검증
        List<Long> notFoundProducts = requestProductIds.stream()
                .filter(id -> !products.containsKey(id)).toList();

        if(!notFoundProducts.isEmpty()){
            throw new NotFoundResourceException("주문 상품을 찾을 수 없습니다. [상품 ID] : " + notFoundProducts);
        }

        List<OrderItem> orderItems = orderItemRequests.stream().map(req -> {
            Long productId = req.getProductId();
            return OrderItem.createOrderItem(products.get(productId), req.getQuantity());
        }).toList();

        //Order 생성
        Order order = Order.createOrder(
                user,
                address,
                request.getReceiverName(),
                request.getReceiverPhone(),
                orderItems);
        orderRepository.save(order);
        return new OrderCreateResponse(order.getOrderId());
    }
}
