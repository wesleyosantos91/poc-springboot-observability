package io.github.wesleyosantos91.exception.handler;

import io.github.wesleyosantos91.domain.response.ErrorResponse;
import io.github.wesleyosantos91.exception.core.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The following errors occurred:");
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Validation failed");
        problemDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ObjectNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setTitle("Resource not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }
}