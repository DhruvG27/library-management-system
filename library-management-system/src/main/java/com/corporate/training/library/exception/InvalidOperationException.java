package com.corporate.training.library.exception;

/**
 * Exception thrown when an invalid operation is attempted.
 */
public class InvalidOperationException extends LibraryException {
    
    private final String operation;
    private final String reason;
    
    public InvalidOperationException(String operation, String reason) {
        // TODO: Implement constructor
        // - Create appropriate error message with operation and reason
        // - Set operation and reason fields
        super("Invalid operation '" + operation + "': " + reason, "INVALID_OPERATION");
        this.operation = operation;
        this.reason = reason;
    }
    
    public InvalidOperationException(String operation, String reason, Throwable cause) {
        // TODO: Implement constructor
        // - Create appropriate error message with operation and reason
        // - Set operation and reason fields
        // - Pass cause to parent
        super("Invalid operation '" + operation + "': " + reason, "INVALID_OPERATION", cause);
        this.operation = operation;
        this.reason = reason;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public String getReason() {
        return reason;
    }
}
