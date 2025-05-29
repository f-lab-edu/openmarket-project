package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.order.enums.OrderStatus;
import oort.cloud.openmarket.order.service.OrderService;
import oort.cloud.openmarket.payment.controller.request.PaymentCreateRequest;
import oort.cloud.openmarket.payment.controller.response.PaymentCreateResponse;
import oort.cloud.openmarket.payment.controller.response.PaymentDetailResponse;
import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.repository.PaymentRepository;
import oort.cloud.openmarket.payment.service.data.PaymentDto;
import oort.cloud.openmarket.payment.service.processor.PaymentProcessor;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentProcessor paymentProcessor;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentProcessor paymentProcessor, OrderService orderService, PaymentRepository paymentRepository) {
        this.paymentProcessor = paymentProcessor;
        this.orderService = orderService;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentCreateResponse processPayment(PaymentCreateRequest request){
        //중복된 결제 요청 방지
        Optional<Payment> savedPayment = paymentRepository.findByExternalOrderId(request.getExternalOrderId());
        if(savedPayment.isPresent()){
            return new PaymentCreateResponse(savedPayment.get().getPaymentId());
        }
        //결제 대상 주문 찾기
        Order order = orderService.findByExternalOrderId(request.getExternalOrderId());
        PaymentApiRequest apiRequest = new PaymentApiRequest.Builder()
                                                            .paymentKey(UUID.randomUUID().toString())
                                                            .orderId(request.getExternalOrderId())
                                                            .amount(request.getAmount())
                                                            .build();

        //외부 PG API 결제 요청 (테스트 컨트롤러로 처리함)
        PaymentDto paymentDto = paymentProcessor.process(apiRequest);

        //결제 데이터 저장
        Payment payment = Payment.createPayment(
                order,
                paymentDto.getExternalOrderId(),
                paymentDto.getPaymentKey(),
                paymentDto.getMethod(),
                paymentDto.getTotalAmount(),
                paymentDto.getStatus(),
                paymentDto.getApprovedAt(),
                paymentDto.getRequestedAt()
        );

        //주문 상태 결제완료로 변경
        order.setStatus(OrderStatus.PAID);

        return new PaymentCreateResponse(paymentRepository.save(payment).getPaymentId());
    }

    public PaymentDetailResponse getPaymentDetail(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundResourceException("결제 정보를 찾을 수 없습니다."));
        return new PaymentDetailResponse(payment);
    }
}
