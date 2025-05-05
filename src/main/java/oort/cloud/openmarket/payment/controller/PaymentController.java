package oort.cloud.openmarket.payment.controller;

import oort.cloud.openmarket.payment.controller.request.PaymentCreateRequest;
import oort.cloud.openmarket.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/v1/payment/approve")
    public ResponseEntity<Long> approvePayment(@RequestBody PaymentCreateRequest request){
        return ResponseEntity.ok().body(
                paymentService.processPayment(request)
        );
    }
}
