package com.corporate.training.library.exception;

import java.util.List;

/**
 * Exception thrown when validation fails.
 */
public class ValidationException extends LibraryException {
    
    private final List<String> validationErrors;
    
    public ValidationException(String message) {
        // TODO: Implement constructor
        // - Call super constructor with message
        // - Set validationErrors to null
        super(message, "VALIDATION_ERROR");
        this.validationErrors = null;
    }
    
    public ValidationException(String message, List<String> validationErrors) {
        // TODO: Implement constructor
        // - Call super constructor with message
        // - Set validationErrors field
        super(message, "VALIDATION_ERROR");
        this.validationErrors = validationErrors;
    }
    
    public ValidationException(String message, List<String> validationErrors, Throwable cause) {
        // TODO: Implement constructor
        // - Call super constructor with message and cause
        // - Set validationErrors field
        super(message, "VALIDATION_ERROR", cause);
        this.validationErrors = validationErrors;
    }
    
    public List<String> getValidationErrors() {
        return validationErrors;
    }
    
    public boolean hasValidationErrors() {
        // TODO: Implement - return true if validationErrors is not null and not empty
        return false;
    }
}
