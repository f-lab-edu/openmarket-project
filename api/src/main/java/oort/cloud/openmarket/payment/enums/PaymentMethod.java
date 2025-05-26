package oort.cloud.openmarket.payment.enums;

import java.util.Arrays;

public enum PaymentMethod {
    CARD("카드"), //카드
    EASY_PAY("간편결제"), //간편결제
    VIRTUAL_ACCOUNT("가상계좌"), //가상계좌
    MOBILE_PHONE("휴대폰"), //휴대폰
    TRANSFER("계좌이체"), //계좌이체
    ;
    private final String label;
    PaymentMethod(String label) {
        this.label = label;
    }

    public static PaymentMethod fromLabel(String label) {
        return Arrays.stream(values())
                .filter(pm -> pm.label.equals(label))
                .findFirst()
                .orElseThrow();
    }
}
