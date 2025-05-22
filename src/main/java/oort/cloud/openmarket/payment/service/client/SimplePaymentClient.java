package oort.cloud.openmarket.payment.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import oort.cloud.openmarket.common.exception.business.ExternalApiException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import oort.cloud.openmarket.payment.service.request.PaymentCancelRequest;
import oort.cloud.openmarket.payment.service.response.ExternalApiErrorResponse;
import oort.cloud.openmarket.payment.service.response.SimplePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class SimplePaymentClient {
    private RestClient restClient;

    @Value("${external.payment.url}")
    private String simplePaymentUrl;

    private final ObjectMapper objectMapper;

    public SimplePaymentClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    void init(){
        restClient = RestClient.create(simplePaymentUrl);
    }


    /**
     *  Client 에러일 경우 재시도를 하지 않고 에러를 던짐
     *  Server 에러일 경우 3번의 재시도 후 에러를 던짐
     */
    @Retryable(
            retryFor = HttpServerErrorException.class,
            maxAttempts = 3,
            backoff = @Backoff(value = 1000, multiplier = 3)
    )
    public SimplePaymentResponse approve(PaymentApproveRequest request){
        try {
            return restClient
                    .post()
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<SimplePaymentResponse>() {});
        }catch (HttpClientErrorException e){
            throw createPaymentApiException(e);
        }
    }

    @Retryable(
            retryFor = HttpServerErrorException.class,
            maxAttempts = 3,
            backoff = @Backoff(value = 1000, multiplier = 3)
    )
    public SimplePaymentResponse cancel(String paymentKey, PaymentCancelRequest request) {
        try {
            return restClient
                    .post()
                    .uri(uriBuilder -> uriBuilder.path("/" + paymentKey).build())
                    .body(request)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<SimplePaymentResponse>() {});
        }catch (HttpClientErrorException e){
            throw createPaymentApiException(e);
        }
    }

    /**
     *       외부 결제 API 서버 에러 발생시 단순히 예외만 던짐
     *       추 후 별도의 처리가 필요할 경우 수정이 필요
     */
    @Recover
    public SimplePaymentResponse approvePaymentRecover(HttpServerErrorException e, PaymentApproveRequest request) {
        throw createPaymentApiException(e);
    }

    @Recover
    public SimplePaymentResponse cancelPaymentRecover(HttpServerErrorException e, String paymentKey, PaymentCancelRequest request) {
        throw createPaymentApiException(e);
    }

    private ExternalApiException createPaymentApiException(HttpStatusCodeException e) {
        try {
            ExternalApiErrorResponse response =
                    objectMapper.readValue(e.getResponseBodyAsString(), ExternalApiErrorResponse.class);
            log.error("[Payment] API Error Code: {}, Error Message {}", response.getErrorCode(), response.getMessage());
            return new ExternalApiException(ErrorType.INTERNAL_ERROR, response.getMessage());
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("[Payment] ErrorResponse Parsing Error", ex);
        }
    }
}
