package io.cmartinezs.dmd.app.controller;

import com.fasterxml.jackson.core.JsonParseException;
import io.cmartinezs.dmd.app.response.ErrorDetail;
import io.cmartinezs.dmd.app.response.ErrorResponse;
import io.cmartinezs.dmd.domain.exception.DnaMatrixException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Carlos
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String COMMON_EXCEPTION_MESSAGE_LOG = "An exception has occurred: {}";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exception(Exception exception){
        log.error(COMMON_EXCEPTION_MESSAGE_LOG.concat(" | {}"), exception.getClass().getSimpleName(), exception.getMessage());
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .message("Unfortunately at this time the service is having problems")
                        .build());
    }

    @ExceptionHandler({JsonParseException.class})
    public ResponseEntity<ErrorResponse> jsonParseException(JsonParseException exception){
        log.error(COMMON_EXCEPTION_MESSAGE_LOG, exception.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message("The request structure is not valid")
                        .build());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception){
        log.error(COMMON_EXCEPTION_MESSAGE_LOG, exception.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message("The request structure is not valid")
                        .build());
    }

    @ExceptionHandler({DnaMatrixException.class})
    public ResponseEntity<ErrorResponse> dnaMatrixException(DnaMatrixException dnaMatrixException) {
        log.error(COMMON_EXCEPTION_MESSAGE_LOG, dnaMatrixException.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message(dnaMatrixException.getMessage())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        log.error(COMMON_EXCEPTION_MESSAGE_LOG, exception.getMessage());
        List<ErrorDetail> fieldErrors = exception.getAllErrors()
                .stream()
                .map(objectError -> {
                    String errorFieldName = getErrorFieldName(objectError);
                    return ErrorDetail.builder()
                            .field(errorFieldName)
                            .detail(List.of(Objects.requireNonNull(objectError.getDefaultMessage())))
                            .build();
                })
                .collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .message("Some fields of the request are not valid")
                        .fieldErrors(fieldErrors)
                        .build());
    }

    private static String getErrorFieldName(ObjectError error) {
        return error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName();
    }
}
