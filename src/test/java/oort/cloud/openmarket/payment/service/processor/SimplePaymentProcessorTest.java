package oort.cloud.openmarket.payment.service.processor;

import oort.cloud.openmarket.common.exception.business.ExternalApiException;
import oort.cloud.openmarket.payment.service.client.SimplePaymentClient;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SimplePaymentProcessorTest {
    @Autowired
    SimplePaymentProcessor processor;

    @SpyBean
    SimplePaymentClient simplePaymentClient;

    @Test
    @DisplayName("PG서버_오류시_3번_재시도_후_예외던짐")
    void fail_pg_request_retry_3() {
        PaymentApproveRequest req = mock();
        String jsonBody = """
                        {
                          "errorCode": "PG_ERROR",
                          "message": "결제 서버 오류"
                        }
                        """;
        doThrow(HttpServerErrorException.create(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "500",
                HttpHeaders.EMPTY,
                jsonBody.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8
        ))
                .when(simplePaymentClient).approve(any());

        assertThatThrownBy(() -> processor.approve(req))
                .isInstanceOf(ExternalApiException.class);

        verify(simplePaymentClient, times(3)).approve(any());
    }

}