package com.ref.api.util.errors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    
    public HttpStatus getStatus() {
        return status;
    }

    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
       this.message = message;
    }


    
    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
        
    }

   

//    private void addValidationError(String object, String field, Object rejectedValue, String message) {
//        addSubError(new ApiValidationError(object, field, rejectedValue, message));
//    }
//
//    private void addValidationError(String object, String message) {
//        addSubError(new ApiValidationError(object, message));
//    }

//    private void addValidationError(FieldError fieldError) {
//        this.addValidationError(
//                fieldError.getObjectName(),
//                fieldError.getField(),
//                fieldError.getRejectedValue(),
//                fieldError.getDefaultMessage());
//    }
//
//    void addValidationErrors(List<FieldError> fieldErrors) {
//        fieldErrors.forEach(this::addValidationError);
//    }
//
//    private void addValidationError(ObjectError objectError) {
//        this.addValidationError(
//                objectError.getObjectName(),
//                objectError.getDefaultMessage());
//    }
//
//    void addValidationError(List<ObjectError> globalErrors) {
//        globalErrors.forEach(this::addValidationError);
//    }
//
//    /**
//     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
//     * @param cv the ConstraintViolation
//     */
//    private void addValidationError(ConstraintViolation<?> cv) {
//        this.addValidationError(
//                cv.getRootBeanClass().getSimpleName(),
//                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
//                cv.getInvalidValue(),
//                cv.getMessage());
//    }
//
//    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
//        constraintViolations.forEach(this::addValidationError);
//    }

}
