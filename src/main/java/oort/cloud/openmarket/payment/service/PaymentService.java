package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.common.exception.business.NotFoundResourceException;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.order.enums.OrderStatus;
import oort.cloud.openmarket.payment.controller.response.PaymentCreateResponse;
import oort.cloud.openmarket.payment.controller.response.PaymentDetailResponse;
import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.repository.PaymentRepository;
import oort.cloud.openmarket.payment.service.data.PaymentResponse;
import oort.cloud.openmarket.payment.service.processor.PaymentProcessor;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import oort.cloud.openmarket.payment.service.request.PaymentCancelRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentProcessor<SimplePaymentResponse> paymentProcessor;
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentProcessor<SimplePaymentResponse> paymentProcessor, PaymentRepository paymentRepository) {
        this.paymentProcessor = paymentProcessor;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentCreateResponse processPayment(Order order){
        //중복된 결제 요청 방지
        Optional<Payment> savedPayment = paymentRepository.findByExternalOrderId(order.getExternalOrderId());
        if(savedPayment.isPresent()){
            return new PaymentCreateResponse(savedPayment.get().getPaymentId());
        }
        //결제 대상 주문 찾기
        PaymentApproveRequest apiRequest = new PaymentApproveRequest.Builder()
                                                            .paymentKey(UUID.randomUUID().toString())
                                                            .orderId(order.getExternalOrderId())
                                                            .amount(order.getTotalAmount())
                                                            .build();

        //외부 PG API 결제 요청 (테스트 컨트롤러로 처리함)
        PaymentResponse<SimplePaymentResponse> response = paymentProcessor.approve(apiRequest);

        //결제 데이터 저장
        Payment payment = Payment.createPayment(
                order,
                response.getData().getExternalOrderId(),
                response.getData().getPaymentKey(),
                response.getData().getMethod(),
                response.getData().getTotalAmount(),
                response.getData().getStatus(),
                response.getData().getApprovedAt(),
                response.getData().getRequestedAt()
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

    @Transactional
    public PaymentDetailResponse cancel(String externalOrderId, String reason) {
        //주문 아이디로 결제 정보 조회
        Payment payment = paymentRepository.findByExternalOrderId(externalOrderId)
                .orElseThrow(() -> new NotFoundResourceException("결제 정보를 찾을 수 없습니다."));

        PaymentCancelRequest request =
                new PaymentCancelRequest(reason);
        //PG 결제 취소 요청
        PaymentResponse<SimplePaymentResponse> response = paymentProcessor.cancel(payment.getPaymentKey(), request);

        //결제 취소 내용 DB 저장
        payment.setCancelReason(reason);
        payment.setCanceledAt(response.getData().getLatestCanceledAt());
        payment.setStatus(response.getData().getStatus());

        return new PaymentDetailResponse(payment);
    }
}
