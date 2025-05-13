package oort.cloud.openmarket.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 *  PG사의 결제 요청 테스트 컨트롤러 입니다.
 *  보낸 요청에 따라 성공, 실패 응답을 제공합니다.
 *  또한 서버에 응답 지연을 재현합니다.
 */
@RestController
@RequestMapping("/test/payment")
public class PaymentTestController {

    private final ObjectMapper objectMapper;

    public PaymentTestController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<TestResponse> paymentTest(@RequestBody PaymentApiRequest request) throws JsonProcessingException {
        if(request.getPaymentKey().equals("fail")){
            return ResponseEntity.badRequest().body(
                    new TestErrorResponse("TestErrorCode", "테스트 에러 입니다")
            );
        }
        return ResponseEntity.ok().body(
                        new TestSuccessResponse(
                            request.getPaymentKey(),
                            request.getExternalOrderId(),
                            "카드",
                            request.getAmount(),
                            "DONE",
                            LocalDateTime.now(),
                            LocalDateTime.now()
        ));
    }
    static class TestResponse{
    }
    static class TestSuccessResponse extends TestResponse{
        private String paymentKey;
        private String externalOrderId;
        private String method;
        private int totalAmount;
        private String status;
        private LocalDateTime requestedAt;
        private LocalDateTime approvedAt;

        public TestSuccessResponse(String paymentKey,
                                   String externalOrderId,
                                   String method, int totalAmount,
                                   String status,
                                   LocalDateTime requestedAt,
                                   LocalDateTime approvedAt) {
            this.paymentKey = paymentKey;
            this.externalOrderId = externalOrderId;
            this.method = method;
            this.totalAmount = totalAmount;
            this.status = status;
            this.requestedAt = requestedAt;
            this.approvedAt = approvedAt;
        }
    }
    static class TestErrorResponse extends TestResponse{
        private String errorCode;
        private String message;

        public TestErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }

}
