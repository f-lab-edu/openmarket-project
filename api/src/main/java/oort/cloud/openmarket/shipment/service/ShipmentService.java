package oort.cloud.openmarket.shipment.service;

import oort.cloud.openmarket.common.exception.business.NotAllowedActionException;
import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.common.paging.offset.OffsetPageResponse;
import oort.cloud.openmarket.order.entity.OrderItem;
import oort.cloud.openmarket.order.enums.OrderItemStatus;
import oort.cloud.openmarket.order.service.OrderItemService;
import oort.cloud.openmarket.shipment.controller.request.ShipmentCreateRequest;
import oort.cloud.openmarket.shipment.controller.request.ShipmentSearchRequest;
import oort.cloud.openmarket.shipment.controller.response.ShipmentCreateResponse;
import oort.cloud.openmarket.shipment.controller.response.ShipmentResponse;
import oort.cloud.openmarket.shipment.entity.Shipment;
import oort.cloud.openmarket.shipment.enums.ShipmentStatus;
import oort.cloud.openmarket.shipment.repository.ShipmentQueryDslRepository;
import oort.cloud.openmarket.shipment.repository.ShipmentRepository;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final ShipmentQueryDslRepository shipmentQueryDslRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, UserService userService, OrderItemService orderItemService, ShipmentQueryDslRepository shipmentQueryDslRepository) {
        this.shipmentRepository = shipmentRepository;
        this.userService = userService;
        this.orderItemService = orderItemService;
        this.shipmentQueryDslRepository = shipmentQueryDslRepository;
    }

    @Transactional
    public ShipmentCreateResponse createShipment(Long userId, ShipmentCreateRequest request){
        // 유저 조회
        Users user = userService.findUserEntityById(userId);

        // 판매자 상품 검증
        OrderItem orderItem = orderItemService.findOrderItemById(request.getOrderItemId());
        if(!orderItem.getProduct().getUser().equals(user)){
            throw new NotAllowedActionException("주문상품 중 본인 소유가 아닌 상품이 포함되어 있어 배송 등록이 불가능합니다.");
        }

        // 주문 상품 배송중 상태 변경
        orderItem.shipping();

        Shipment shipment = Shipment.createShipment(
                user,
                request.getTrackingNumber(),
                request.getDeliveryCompany(),
                orderItem
        );

        Long shipmentId = shipmentRepository.save(shipment).getShipmentId();
        return new ShipmentCreateResponse(shipmentId);
    }

    public OffsetPageResponse<ShipmentResponse> getShipmentListByUserId(
            Long userId, Pageable pageable, ShipmentSearchRequest request){
        return shipmentQueryDslRepository.getShipmentList(userId, pageable, request);
    }

    @Transactional
    public void modifyStatus(Long shipmentId, ShipmentStatus status) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new NotFoundResourceException("조회된 배송 정보가 없습니다."));
        LocalDateTime deliveredAt = LocalDateTime.now();

        shipment.setStatus(ShipmentStatus.DELIVERED);
        shipment.setDeliveredAt(deliveredAt);

        OrderItem orderItem = shipment.getOrderItem();
        orderItem.setStatus(OrderItemStatus.DELIVERED);
        orderItem.setDeliveredAt(deliveredAt);
    }
}
