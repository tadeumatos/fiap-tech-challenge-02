package br.com.fiap.tech.challenge._2.exceptions.global;

import br.com.fiap.tech.challenge._2.exceptions.BusinessException;
import br.com.fiap.tech.challenge._2.exceptions.EntityInUseException;
import br.com.fiap.tech.challenge._2.exceptions.ResourceNotFoundException;
import br.com.fiap.tech.challenge._2.exceptions.ValidationFieldsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request)
    {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (first, second) -> first
                ));

        return createProblemDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Validation",
                exception.getMessage(),
                request,
                errors.toString()
        );
    }

    @ExceptionHandler(ValidationFieldsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail handleValidationFieldsException(
            ValidationFieldsException exception,
            HttpServletRequest request) {

        return createProblemDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Validation",
                exception.getMessage(),
                request,
                null
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleIllegalArgumentException(
            IllegalArgumentException exception,
            HttpServletRequest request) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid request",
                exception.getMessage(),
                request,
                null
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request)
    {
        return createProblemDetail(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                exception.getMessage(),
                request,
                null);
    }

    @ExceptionHandler(EntityInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleConflictException(EntityInUseException exception,HttpServletRequest request)
    {
        return createProblemDetail(
                HttpStatus.CONFLICT,
                "Register in use",
                exception.getMessage(),
                request,
                null);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleConflictException(BusinessException exception,HttpServletRequest request)
    {
        return createProblemDetail(
                HttpStatus.CONFLICT,
                "Error rule business",
                exception.getMessage(),
                request,
                null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleException(Exception exception,HttpServletRequest request)
    {
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error Internal Error Server",
                exception.getMessage(),
                request,
                null);
    }


    private ProblemDetail createProblemDetail(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request,
            String errors) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);
        if (StringUtils.hasText(errors)) {
            problemDetail.setProperty("errors", errors);
        }

        problemDetail.setType(
                URI.create(
                        "https://api.techchallenge.com/errors/business-rule"
                )
        );

        problemDetail.setInstance(
                URI.create(request.getRequestURI())
        );

        problemDetail.setProperty(
                "timestamp",
                LocalDateTime.now()
        );

        return problemDetail;
    }

}
