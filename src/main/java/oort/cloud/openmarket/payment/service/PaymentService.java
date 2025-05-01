package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.order.service.OrderService;
import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.enums.PaymentStatus;
import oort.cloud.openmarket.payment.service.request.SimplePaymentApiRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.PriorityQueue;

@Service
public class PaymentService {
    private final PaymentProcessor paymentProcessor;
    private final OrderService orderService;

    public PaymentService(PaymentProcessor paymentProcessor, OrderService orderService) {
        this.paymentProcessor = paymentProcessor;
        this.orderService = orderService;
    }

    @Transactional
    public void processPayment(SimplePaymentApiRequest request){
        //주문 조회
        Order order = orderService.findById(request.getOrderId());

        //외부 PG API 결제 요청 (가상으로 처리함)

        //결제 처리 여부에 따른 처리

        //결제 데이터 저장
        Payment.createPayment(order, request.getPaymentMethod(), request.getAmount(), PaymentStatus.PAID);
    }

    public void cancelPayment(Long paymentId){
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a));

    }

}
