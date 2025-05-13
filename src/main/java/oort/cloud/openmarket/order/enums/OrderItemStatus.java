package oort.cloud.openmarket.order.enums;

import java.util.EnumSet;

public enum OrderItemStatus {
    ORDERED,     // 주문됨
    SHIPPED,     // 배송중
    DELIVERED,   // 배송완료
    CANCELLED,   // 주문 취소
    RETURNED     // 반품
    ;

    private static final EnumSet<OrderItemStatus> CANCELLABLE_STATUSES =
            EnumSet.of(ORDERED);

    public boolean isCancellable() {
        return CANCELLABLE_STATUSES.contains(this);
    }
}
