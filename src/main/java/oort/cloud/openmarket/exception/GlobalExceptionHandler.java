package oort.cloud.openmarket.exception;

import lombok.extern.slf4j.Slf4j;
import oort.cloud.openmarket.exception.response.InvalidParameterExceptionResponse;
import oort.cloud.openmarket.exception.response.AuthExceptionResponse;
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

    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<AuthExceptionResponse> handleUserServiceException(AuthServiceException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new AuthExceptionResponse(e.getErrorType().getMessage(), e.getErrorType())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidParameterExceptionResponse> handleInvalidParameter(MethodArgumentNotValidException e){
        log.error("[Request] Exception Message : {} Class Name : {}", e.getMessage(), e.getClass().getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new InvalidParameterExceptionResponse(getFieldErrorDetails(e), ErrorType.INVALID_PARAMETER)
                );
    }

    private List<InvalidParameterExceptionResponse.FieldErrorDetail> getFieldErrorDetails(MethodArgumentNotValidException e){
        return e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new InvalidParameterExceptionResponse.FieldErrorDetail(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }
}
