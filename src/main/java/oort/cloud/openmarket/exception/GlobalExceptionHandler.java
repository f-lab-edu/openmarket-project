package oort.cloud.openmarket.exception;

import lombok.extern.slf4j.Slf4j;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.exception.response.RequestValidationErrorResponse;
import oort.cloud.openmarket.exception.response.ApiExceptionResponse;
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

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiExceptionResponse> handleUserServiceException(BusinessException e){
        return ResponseEntity.status(e.getErrorType().getStatus()).body(
                ApiExceptionResponse.of(e.getMessage(), e.getErrorType())
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
