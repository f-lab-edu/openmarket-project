package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.common.exception.business.ExternalApiException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;
import oort.cloud.openmarket.data.PaymentCreateRequestTest;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.payment.controller.request.PaymentCreateRequest;
import oort.cloud.openmarket.payment.controller.response.PaymentCreateResponse;
import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.enums.PaymentStatus;
import oort.cloud.openmarket.payment.repository.PaymentRepository;
import oort.cloud.openmarket.payment.service.data.PaymentResponse;
import oort.cloud.openmarket.payment.service.processor.PaymentProcessor;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentProcessor<SimplePaymentResponse> paymentProcessor;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentProcessor = mock(PaymentProcessor.class);
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentService(paymentProcessor, paymentRepository);
    }

    @Test
    @DisplayName("중복된 externalOrderId 요청일 경우 기존 paymentId 반환")
    void fail_exist_external_order_id() {
        // given
        Long savedPaymentId = 123L;
        Order mockOrder = mock(Order.class);
        Payment savedPayment = mock(Payment.class);

        when(mockOrder.getExternalOrderId()).thenReturn("ulid-1234");
        when(savedPayment.getPaymentId()).thenReturn(savedPaymentId);
        when(paymentRepository.findByExternalOrderId("ulid-1234"))
                .thenReturn(Optional.of(savedPayment));

        // when
        PaymentCreateResponse result = paymentService.processPayment(mockOrder);

        // then
        assertThat(result.getPaymentId()).isEqualTo(savedPaymentId);
        verify(paymentProcessor, never()).approve(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("새로운 externalOrderId이면 새로운 payment 생성됨")
    void suceess_new_payment() {
        // given
        String externalOrderId = "ulid-new";
        int amount = 15000;
        Long newPaymentId = 456L;
        Order mockOrder = mock(Order.class);
        PaymentCreateRequest request = new PaymentCreateRequestTest(externalOrderId, amount);
        Order order = mock(Order.class);

        PaymentResponse<SimplePaymentResponse> response = mock(PaymentResponse.class);
        SimplePaymentResponse simpleResponse = mock(SimplePaymentResponse.class);

        when(response.getData()).thenReturn(simpleResponse);
        when(simpleResponse.getExternalOrderId()).thenReturn("external-order-id");
        when(simpleResponse.getPaymentKey()).thenReturn("pay-key-123");
        when(simpleResponse.getMethod()).thenReturn("카드");
        when(simpleResponse.getTotalAmount()).thenReturn(10000);
        when(simpleResponse.getStatus()).thenReturn(PaymentStatus.DONE);
        when(simpleResponse.getApprovedAt()).thenReturn(LocalDateTime.now());
        when(simpleResponse.getRequestedAt()).thenReturn(LocalDateTime.now().minusMinutes(1));

        when(paymentRepository.findByExternalOrderId(externalOrderId))
                .thenReturn(Optional.empty());

        when(paymentProcessor.approve(any(PaymentApproveRequest.class))).thenReturn(response);

        Payment savedPayment = mock(Payment.class);
        when(savedPayment.getPaymentId()).thenReturn(newPaymentId);
        when(paymentRepository.save(any())).thenReturn(savedPayment);

        // when
        Long result = paymentService.processPayment(order).getPaymentId();

        // then
        assertThat(result).isEqualTo(newPaymentId);
        verify(paymentProcessor).approve(any(PaymentApproveRequest.class));
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("PG API 요청에서 에러가 발생할 경우 Payment는 저장되지 않는다.")
    void fail_throw_payment_processor() {
        // given
        String externalOrderId = "ulid-new";
        int amount = 15000;

        PaymentCreateRequest request = new PaymentCreateRequestTest(externalOrderId, amount);

        when(paymentRepository.findByExternalOrderId(externalOrderId))
                .thenReturn(Optional.empty());
        Order order = mock(Order.class);

        when(paymentProcessor.approve(any(PaymentApproveRequest.class))).thenThrow(
                new ExternalApiException(ErrorType.INTERNAL_ERROR, "외부 API 결제 에러"));

        // when
        assertThatThrownBy(() -> paymentService.processPayment(order)).isInstanceOf(ExternalApiException.class);

        // then
        verify(paymentRepository, never()).save(any());
    }
}