package oort.cloud.openmarket.order.enums;

import java.util.EnumSet;

public enum OrderStatus {
    CREATED,         // 주문 생성
    PAYMENT_PENDING, // 결재 대기
    PAID,            // 결재 완료

    CANCELLED        // 주문 취소됨
    ;

    private static final EnumSet<OrderStatus> CANCELLABLE_STATUSES =
            EnumSet.of(CREATED, PAYMENT_PENDING, PAID);

    public boolean isCancellable() {
        return CANCELLABLE_STATUSES.contains(this);
    }
}
