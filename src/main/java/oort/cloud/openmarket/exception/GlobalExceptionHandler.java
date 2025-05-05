package oort.cloud.openmarket.exception;

import lombok.extern.slf4j.Slf4j;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.exception.response.ApiExceptionResponse;
import oort.cloud.openmarket.exception.response.ExternalApiExceptionResponse;
import oort.cloud.openmarket.exception.response.RequestValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ApiBaseException.class})
    public ResponseEntity<ApiExceptionResponse> handleApiException(ApiBaseException e){
        return ResponseEntity.status(e.getErrorType().getStatus()).body(
                ApiExceptionResponse.of(e.getMessage(), e.getErrorType())
        );
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ExternalApiExceptionResponse> handleExternalApiException(ExternalApiException e){
        return ResponseEntity.status(e.getStatus())
                .body(
                        new ExternalApiExceptionResponse(e.getErrorCode(), e.getMessage())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiExceptionResponse> handleInternalServerException(RuntimeException e){
        log.error("[Internal Ex] Class : {}, Message : {}, ", e.getClass(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiExceptionResponse.of(ErrorType.INTERNAL_ERROR.getMessage(), ErrorType.INTERNAL_ERROR)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestValidationErrorResponse> handleInvalidParameter(MethodArgumentNotValidException e){
        return ResponseEntity.status(ErrorType.INVALID_PARAMETER.getStatus())
                .body(
                        RequestValidationErrorResponse.of(getFieldErrorDetails(e), ErrorType.INVALID_PARAMETER)
                );
    }

    private List<RequestValidationErrorResponse.FieldErrorDetail> getFieldErrorDetails(MethodArgumentNotValidException e){
        return e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new RequestValidationErrorResponse.FieldErrorDetail(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }
}
