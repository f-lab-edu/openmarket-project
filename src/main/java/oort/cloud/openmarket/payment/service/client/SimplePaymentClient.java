package oort.cloud.openmarket.payment.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import oort.cloud.openmarket.exception.external.PaymentApiException;
import oort.cloud.openmarket.exception.response.ExternalApiExceptionResponse;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;
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
public class SimplePaymentClient {
    private RestClient restClient;

    @Value("http://127.0.0.1:8080/test/payment")
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
    public SimplePaymentResponse approvePayment(PaymentApiRequest request){
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

    @Recover
    public SimplePaymentResponse approvePaymentRecover(HttpServerErrorException e, PaymentApiRequest request) {
        throw createPaymentApiException(e);
    }


    private PaymentApiException createPaymentApiException(HttpStatusCodeException e) {
        try {
            ExternalApiExceptionResponse response =
                    objectMapper.readValue(e.getResponseBodyAsString(), ExternalApiExceptionResponse.class);
            return new PaymentApiException(response.getErrorCode(), response.getMessage(), e.getStatusCode().value());
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("[Payment] ErrorResponse Parsing Error", ex);
        }
    }
}
