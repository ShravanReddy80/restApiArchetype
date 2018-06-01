package com.ref.api.util.errors;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {//extends ResponseEntityExceptionHandler {
    
    private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({ MissingServletRequestParameterException.class})
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "There is a problem with the request::: CONTENT_TYPE = " + ex.getContentType() + " not supported", ex);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected ResponseEntity<Object> handleHttpMessageNotReadable(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
       
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            apiError.addSubError(new ApiValidationError(fieldError.getObjectName(), fieldError.getField(),
                    fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
        return buildResponseEntity(apiError);
    }


    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
     * fails.
     *
     * @param ex
     *            the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation error");

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {

            apiError.addSubError(new ApiValidationError(constraintViolation.getRootBeanClass().getSimpleName(),
                    ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
                    constraintViolation.getInvalidValue(), constraintViolation.getMessage()));

        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        logger.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "The entity you are searching for does not exist", ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB
     * causes.
     *
     * @param ex
     *            the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
            WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }
    
    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityExistsException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, "The entity you are trying to insert already exists", ex);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex
     *            the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
   protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
           HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
                "Requested HTTP Method is not supported... Supported Methods: " + ex.getSupportedMethods(), ex);
        return buildResponseEntity(apiError);
    }

   

    @ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE,
                "There is a problem with the request::: Supported MediaTypes = " + ex.getSupportedMediaTypes(), ex);
        return buildResponseEntity(apiError);
    }

//    @ExceptionHandler({ AccessDeniedException.class })
//    protected ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
//
//        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "Access denied", ex);
//        return buildResponseEntity(apiError);
//    }
    
    
    

    
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


}
