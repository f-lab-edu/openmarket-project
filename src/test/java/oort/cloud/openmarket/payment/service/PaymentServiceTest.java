package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.common.exception.business.ExternalApiException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;
import oort.cloud.openmarket.data.PaymentCreateRequestTest;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.order.service.OrderService;
import oort.cloud.openmarket.payment.controller.request.PaymentCreateRequest;
import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.repository.PaymentRepository;
import oort.cloud.openmarket.payment.service.data.PaymentDto;
import oort.cloud.openmarket.payment.service.processor.PaymentProcessor;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentProcessor paymentProcessor;
    private OrderService orderService;
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentProcessor = mock(PaymentProcessor.class);
        orderService = mock(OrderService.class);
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentService(paymentProcessor, orderService, paymentRepository);
    }

    @Test
    @DisplayName("중복된 externalOrderId 요청일 경우 기존 paymentId 반환")
    void fail_exist_external_order_id() {
        // given
        String externalOrderId = "ulid-1234";
        int amount = 10000;
        Long savedPaymentId = 123L;

        PaymentCreateRequest request = new PaymentCreateRequestTest(externalOrderId, amount);
        Payment savedPayment = mock(Payment.class);

        when(savedPayment.getPaymentId()).thenReturn(savedPaymentId);
        when(paymentRepository.findByExternalOrderId("ulid-1234"))
                .thenReturn(Optional.of(savedPayment));


        // when
        Long result = paymentService.processPayment(request).getPaymentId();

        // then
        assertThat(result).isEqualTo(savedPaymentId);
        verify(paymentProcessor, never()).process(any());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    @DisplayName("새로운 externalOrderId이면 새로운 payment 생성됨")
    void suceess_new_payment() {
        // given
        String externalOrderId = "ulid-new";
        int amount = 15000;
        Long newPaymentId = 456L;

        PaymentCreateRequest request = new PaymentCreateRequestTest(externalOrderId, amount);
        Order order = mock(Order.class);

        when(paymentRepository.findByExternalOrderId(externalOrderId))
                .thenReturn(Optional.empty());
        when(orderService.findByExternalOrderId(externalOrderId)).thenReturn(order);

        PaymentDto paymentDto = new PaymentDto.Builder()
                .orderId(externalOrderId)
                .paymentKey(UUID.randomUUID().toString())
                .method("카드")
                .totalAmount(amount)
                .status("DONE")
                .requestedAt(LocalDateTime.now())
                .approvedAt(LocalDateTime.now())
                .build();
        when(paymentProcessor.process(any(PaymentApiRequest.class))).thenReturn(paymentDto);

        Payment savedPayment = mock(Payment.class);
        when(savedPayment.getPaymentId()).thenReturn(newPaymentId);
        when(paymentRepository.save(any())).thenReturn(savedPayment);

        // when
        Long result = paymentService.processPayment(request).getPaymentId();

        // then
        assertThat(result).isEqualTo(newPaymentId);
        verify(paymentProcessor).process(any(PaymentApiRequest.class));
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
        when(orderService.findByExternalOrderId(externalOrderId)).thenReturn(order);

        when(paymentProcessor.process(any(PaymentApiRequest.class))).thenThrow(
                new ExternalApiException(ErrorType.INTERNAL_ERROR, "외부 API 결제 에러"));

        // when
        assertThatThrownBy(() -> paymentService.processPayment(request)).isInstanceOf(ExternalApiException.class);

        // then
        verify(paymentRepository, never()).save(any());
    }
}