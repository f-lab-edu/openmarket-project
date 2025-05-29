package oort.cloud.openmarket.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import oort.cloud.openmarket.payment.service.request.PaymentCancelRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

/**
 *  PG사의 결제 요청 테스트 컨트롤러 입니다.
 *  보낸 요청에 따라 성공, 실패 응답을 제공합니다.
 *  또한 서버에 응답 지연을 재현합니다.
 */
@RestController
@RequestMapping("/test/payment")
public class PaymentTestController {

    @PostMapping
    public ResponseEntity<TestResponse> paymentApproveTest(@RequestBody ApproveRequest request) throws JsonProcessingException {
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
                                OffsetDateTime.now(),
                                OffsetDateTime.now(),
                                null
        ));
    }

    @PostMapping("/{paymentKey}")
    public ResponseEntity<TestResponse> paymentCancelTest(
            @PathVariable("paymentKey") String paymentKey,
            @RequestBody PaymentCancelRequest request) throws JsonProcessingException {
        if(paymentKey.equals("fail")){
            return ResponseEntity.badRequest().body(
                    new TestErrorResponse("TestErrorCode", "테스트 에러 입니다")
            );
        }
        return ResponseEntity.ok().body(
                new TestSuccessResponse(
                        paymentKey,
                        null,
                        "카드",
                        0,
                        "CANCEL",
                        OffsetDateTime.now(),
                        OffsetDateTime.now(),
                        new TestSuccessResponse.Cancel(
                                "transactionKey",
                                request.getCancelReason(),
                                10000,
                                "CANCEL",
                                OffsetDateTime.now()
                        )
                ));
    }
    static class ApproveRequest{
        String paymentKey;
        String externalOrderId;
        int amount;

        public ApproveRequest(String paymentKey, String externalOrderId, int amount) {
            this.paymentKey = paymentKey;
            this.externalOrderId = externalOrderId;
            this.amount = amount;
        }

        public String getPaymentKey() {
            return paymentKey;
        }

        public String getExternalOrderId() {
            return externalOrderId;
        }

        public int getAmount() {
            return amount;
        }
    }
    static class TestResponse{
    }
    static class TestSuccessResponse extends TestResponse{
        private String paymentKey;
        private String externalOrderId;
        private String method;
        private int totalAmount;
        private String status;
        private OffsetDateTime requestedAt;
        private OffsetDateTime approvedAt;
        private List<Cancel> cancels;

        public TestSuccessResponse(String paymentKey,
                                   String externalOrderId,
                                   String method, int totalAmount,
                                   String status,
                                   OffsetDateTime requestedAt,
                                   OffsetDateTime approvedAt,
                                   Cancel cancel) {
            this.paymentKey = paymentKey;
            this.externalOrderId = externalOrderId;
            this.method = method;
            this.totalAmount = totalAmount;
            this.status = status;
            this.requestedAt = requestedAt;
            this.approvedAt = approvedAt;
            this.cancels = cancel == null ? null : List.of(cancel);
        }
        static class Cancel {
            private String transactionKey;
            private String cancelReason;
            private int cancelAmount;
            private String cancelStatus;
            private OffsetDateTime canceledAt;

            public Cancel(String transactionKey, String cancelReason, int cancelAmount, String cancelStatus, OffsetDateTime canceledAt) {
                this.transactionKey = transactionKey;
                this.cancelReason = cancelReason;
                this.cancelAmount = cancelAmount;
                this.cancelStatus = cancelStatus;
                this.canceledAt = canceledAt;
            }

            public String getTransactionKey() {
                return transactionKey;
            }

            public String getCancelReason() {
                return cancelReason;
            }

            public int getCancelAmount() {
                return cancelAmount;
            }

            public String getCancelStatus() {
                return cancelStatus;
            }


            public OffsetDateTime getCanceledAt() {
                return canceledAt;
            }

            @Override
            public String toString() {
                return "Cancel{" +
                        "transactionKey='" + transactionKey + '\'' +
                        ", cancelReason='" + cancelReason + '\'' +
                        ", cancelAmount=" + cancelAmount +
                        ", cancelStatus=" + cancelStatus +
                        ", canceledAt=" + canceledAt +
                        '}';
            }
        }
        public String getPaymentKey() {
            return paymentKey;
        }

        public String getExternalOrderId() {
            return externalOrderId;
        }

        public String getMethod() {
            return method;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public String getStatus() {
            return status;
        }

        public OffsetDateTime getRequestedAt() {
            return requestedAt;
        }

        public OffsetDateTime getApprovedAt() {
            return approvedAt;
        }

        public List<Cancel> getCancels() {
            return cancels;
        }
    }
    static class TestErrorResponse extends TestResponse{
        private String errorCode;
        private String message;

        public TestErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getMessage() {
            return message;
        }
    }

}
