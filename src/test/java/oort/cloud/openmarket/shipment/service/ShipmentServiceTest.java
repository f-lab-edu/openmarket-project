package oort.cloud.openmarket.shipment.service;

import oort.cloud.openmarket.common.exception.business.NotAllowedActionException;
import oort.cloud.openmarket.order.entity.OrderItem;
import oort.cloud.openmarket.order.service.OrderItemService;
import oort.cloud.openmarket.products.entity.Products;
import oort.cloud.openmarket.shipment.controller.request.ShipmentCreateRequest;
import oort.cloud.openmarket.shipment.controller.response.ShipmentCreateResponse;
import oort.cloud.openmarket.shipment.entity.Shipment;
import oort.cloud.openmarket.shipment.repository.ShipmentRepository;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {
    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private UserService userService;

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private ShipmentService shipmentService;

    @Test
    @DisplayName("판매자의 배송 등록이 성공한다.")
    void success_create_shipment(){
        //given
        Long userId = 1L;
        Long orderItemId = 1L;
        Users mockUser = mock(Users.class);
        OrderItem mockOrderItem = mock(OrderItem.class);
        Products mockProducts = mock(Products.class);
        ShipmentCreateRequest request = mock(ShipmentCreateRequest.class);
        Shipment mockShipment = mock(Shipment.class);

        //when
        when(userService.findUserEntityById(userId)).thenReturn(mockUser);
        when(orderItemService.findOrderItemById(orderItemId)).thenReturn(mockOrderItem);
        when(request.getOrderItemId()).thenReturn(orderItemId);
        when(mockOrderItem.getProduct()).thenReturn(mockProducts);
        when(mockProducts.getUser()).thenReturn(mockUser);
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(mockShipment);

        ShipmentCreateResponse response = shipmentService.createShipment(userId, request);

        //then
        Assertions.assertThat(response).isNotNull();
        verify(userService).findUserEntityById(userId);
        verify(orderItemService).findOrderItemById(orderItemId);
        verify(shipmentRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("주문 상품이 판매자의 상품이 아닐 경우 NotAllowedActionException 예외 발생")
    void fail_create_shipment(){
        //given
        Long userId = 1L;
        Long orderItemId = 1L;
        OrderItem mockOrderItem = mock(OrderItem.class);
        Products mockProducts = mock(Products.class);
        ShipmentCreateRequest request = mock(ShipmentCreateRequest.class);

        Users mockUser = mock(Users.class);
        ReflectionTestUtils.setField(mockUser, "userId", 1L);

        Users mockUser2 = mock(Users.class);
        ReflectionTestUtils.setField(mockUser2, "userId", 2L);

        //when
        when(userService.findUserEntityById(userId)).thenReturn(mockUser);
        when(orderItemService.findOrderItemById(orderItemId)).thenReturn(mockOrderItem);
        when(request.getOrderItemId()).thenReturn(orderItemId);
        when(mockOrderItem.getProduct()).thenReturn(mockProducts);
        when(mockProducts.getUser()).thenReturn(mockUser2);

        Assertions.assertThatThrownBy(() -> shipmentService.createShipment(userId, request))
                .isInstanceOf(NotAllowedActionException.class);

    }

}