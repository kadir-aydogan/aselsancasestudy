package tr.com.aselsankadir.casestudy.presentation.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tr.com.aselsankadir.casestudy.domain.common.AselRuntimeException;
import tr.com.aselsankadir.casestudy.presentation.dto.ProcessResult;
import tr.com.aselsankadir.casestudy.presentation.dto.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AselControllerAdvice {

    // domain ve iş akışı hatalarım
    @ExceptionHandler(AselRuntimeException.class)
    public ResponseEntity<ResponseBody<Void>> handleAselRuntimeException(AselRuntimeException ex) {
        log.warn("AselRuntimeException: {}", ex.getMessage());
        ResponseBody<Void> response = ResponseBody.error(ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    // request bodylerin @Valid hataları
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());

        ResponseBody<Void> response = new ResponseBody<>(null, ProcessResult.ERROR, null);
        response.getMessages().addAll(messages);

        return ResponseEntity.badRequest().body(response);
    }

    // path variable & request param. vs. hataları
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseBody<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        ResponseBody<Void> response = new ResponseBody<>(null, ProcessResult.ERROR, null);
        response.getMessages().addAll(
                ex.getConstraintViolations().stream()
                        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                        .toList()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> handleGeneralException(Exception ex) {
        log.error("Beklenmeyen hata oluştu", ex);
        ResponseBody<Void> response = ResponseBody.error("Sunucu hatası: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    private String formatFieldError(FieldError fieldError) {
        return "%s: %s".formatted(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
