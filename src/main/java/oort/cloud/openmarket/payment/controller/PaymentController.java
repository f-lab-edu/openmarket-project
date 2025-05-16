package oort.cloud.openmarket.payment.controller;

import oort.cloud.openmarket.payment.controller.response.PaymentDetailResponse;
import oort.cloud.openmarket.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/v1/payment/{paymentId}")
    public ResponseEntity<PaymentDetailResponse> getPaymentDetail(
            @PathVariable("paymentId") Long paymentId){
        return ResponseEntity.ok()
                .body(
                        paymentService.getPaymentDetail(paymentId)
                );
    }

}
