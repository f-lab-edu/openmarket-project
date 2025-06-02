package oort.cloud.openmarket.order.service;

import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.order.entity.OrderItem;
import oort.cloud.openmarket.order.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem findOrderItemById(Long orderItemId){
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundResourceException("조회된 주문 상품이 없습니다."));
    }

    public List<OrderItem> findOrderItemsByIds(List<Long> orderItemIds){
        List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);
        if(orderItems.isEmpty()){
            throw new NotFoundResourceException("조회된 주문 상품이 없습니다.");
        }
        return orderItems;
    }
}
