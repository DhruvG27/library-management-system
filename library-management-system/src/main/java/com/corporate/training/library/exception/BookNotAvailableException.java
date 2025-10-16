package com.corporate.training.library.exception;

/**
 * Exception thrown when a book is not available for borrowing.
 */
public class BookNotAvailableException extends LibraryException {
    
    private final String isbn;
    private final String reason;
    
    public BookNotAvailableException(String isbn, String reason) {
        // TODO: Implement constructor
        // - Create appropriate error message including reason
        // - Set isbn and reason fields
        super("Book with ISBN " + isbn + " is not available: " + reason, "BOOK_NOT_AVAILABLE");
        this.isbn = isbn;
        this.reason = reason;
    }
    
    public BookNotAvailableException(String isbn, String reason, Throwable cause) {
        // TODO: Implement constructor
        // - Create appropriate error message including reason
        // - Set isbn and reason fields
        // - Pass cause to parent
        super("Book with ISBN " + isbn + " is not available: " + reason, "BOOK_NOT_AVAILABLE", cause);
        this.isbn = isbn;
        this.reason = reason;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public String getReason() {
        return reason;
    }
}
