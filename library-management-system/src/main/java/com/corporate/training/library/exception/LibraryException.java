package com.corporate.training.library.exception;

/**
 * Base exception class for all library-related exceptions.
 * Students need to implement proper constructors and exception handling.
 */
public class LibraryException extends Exception {
    
    private final String errorCode;
    
    public LibraryException(String message) {
        // TODO: Implement constructor
        // - Call super constructor with message
        // - Set errorCode to null
        super(message);
        this.errorCode = null;
    }
    
    public LibraryException(String message, String errorCode) {
        // TODO: Implement constructor
        // - Call super constructor with message
        // - Set errorCode
        super(message);
        this.errorCode = errorCode;
    }
    
    public LibraryException(String message, Throwable cause) {
        // TODO: Implement constructor
        // - Call super constructor with message and cause
        // - Set errorCode to null
        super(message, cause);
        this.errorCode = null;
    }
    
    public LibraryException(String message, String errorCode, Throwable cause) {
        // TODO: Implement constructor
        // - Call super constructor with message and cause
        // - Set errorCode
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
