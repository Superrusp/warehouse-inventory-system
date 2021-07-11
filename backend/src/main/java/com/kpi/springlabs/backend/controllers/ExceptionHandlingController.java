package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.exception.BadRequestException;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAllUncaughtException(Exception exception, HttpServletRequest request) {
        LOG.error("Unknown error occurred", exception);
        return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Principal user = request.getUserPrincipal();
        LOG.error("Access denied for user '{}' to resource '{}'",
                Objects.isNull(user) ? "" : user.getName(),
                requestURI);
        return buildErrorResponse(exception.getMessage(), HttpStatus.FORBIDDEN, requestURI);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectNotFoundException(ObjectNotFoundException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        LOG.error("Failed to find the requested object in '{}'", requestURI);
        return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND, requestURI);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        LOG.error("Failed to process bad request in '{}'", requestURI);
        return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, requestURI);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException exception, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        LOG.error("Conflict occurred while request processing in '{}'", requestURI);
        return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT, requestURI);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String requestURI = getRequestURI(request);
        LOG.error("Validation of {} parameter(s) failed in '{}'", ex.getErrorCount(), requestURI);
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        if (CollectionUtils.isEmpty(errors)) {
            ErrorResponse errorResponse = buildErrorResponse("", status, requestURI);
            return ResponseEntity.status(status)
                    .body(errorResponse);
        }

        String errorMessage = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
        ErrorResponse errorResponse = buildErrorResponse(errorMessage, status, requestURI);
        return ResponseEntity.status(status)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String requestURI = getRequestURI(request);
        LOG.error("Failed to deserialize request to '{}'", requestURI);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(ex.getMessage(), status, requestURI));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        String requestURI = getRequestURI(request);
        LOG.error("Type conversion failed in '{}'", requestURI);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(ex.getMessage(), status, requestURI));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        String requestURI = getRequestURI(request);
        LOG.error("Missing request parameter in '{}'", requestURI);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(ex.getMessage(), status, requestURI));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        String requestURI = getRequestURI(request);
        LOG.error("Request method '{}' not supported for end point '{}'", ex.getMethod(), requestURI);
        return ResponseEntity.status(status)
                .body(buildErrorResponse(ex.getMessage(), status, requestURI));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        LOG.error("", ex);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private String getRequestURI(WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        return servletWebRequest.getRequest().getRequestURI();
    }

    private ErrorResponse buildErrorResponse(String errorMessage, HttpStatus status, String path) {
        return ErrorResponse.builder()
                .path(path)
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(errorMessage)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
