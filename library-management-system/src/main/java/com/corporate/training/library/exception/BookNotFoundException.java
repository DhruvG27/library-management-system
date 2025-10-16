package com.corporate.training.library.exception;

/**
 * Exception thrown when a book is not found in the library.
 */
public class BookNotFoundException extends LibraryException {
    
    private final String isbn;
    
    public BookNotFoundException(String isbn) {
        // TODO: Implement constructor
        // - Create appropriate error message
        // - Set ISBN field
        super("Book not found with ISBN: " + isbn, "BOOK_NOT_FOUND");
        this.isbn = isbn;
    }
    
    public BookNotFoundException(String isbn, Throwable cause) {
        // TODO: Implement constructor
        // - Create appropriate error message
        // - Set ISBN field
        // - Pass cause to parent
        super("Book not found with ISBN: " + isbn, "BOOK_NOT_FOUND", cause);
        this.isbn = isbn;
    }
    
    public String getIsbn() {
        return isbn;
    }
}
