package com.corporate.training.library.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Book entity representing a book in the library system.
 * Students need to implement proper equals, hashCode, and toString methods.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private LocalDate publishedDate;
    private boolean isActive;

    // Default constructor
    public Book() {
    }

    // Constructor with essential fields
    public Book(String isbn, String title, String author, String category, int totalCopies) {
        // TODO: Implement constructor with validation
        // - ISBN should not be null or empty
        // - Title should not be null or empty
        // - Author should not be null or empty
        // - Category should not be null or empty
        // - Total copies should be positive
        // - Available copies should be set to total copies initially
        // - Published date should be set to current date
        // - isActive should be set to true
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        // TODO: Add validation - ISBN should not be null or empty
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        // TODO: Add validation - Title should not be null or empty
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        // TODO: Add validation - Author should not be null or empty
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        // TODO: Add validation - Category should not be null or empty
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        // TODO: Add validation - Total copies should be positive
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        // TODO: Add validation - Available copies should not be negative
        // and should not exceed total copies
        this.availableCopies = availableCopies;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        // TODO: Add validation - Published date should not be null
        // and should not be in the future
        this.publishedDate = publishedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Business methods
    public boolean isAvailable() {
        // TODO: Implement - return true if book is active and has available copies
        return false;
    }

    public void borrowCopy() {
        // TODO: Implement - decrement available copies if book is available
        // Throw appropriate exception if not available
    }

    public void returnCopy() {
        // TODO: Implement - increment available copies if not exceeding total copies
        // Throw appropriate exception if invalid operation
    }

    @Override
    public boolean equals(Object o) {
        // TODO: Implement proper equals method
        // Two books are equal if they have the same ISBN
        return false;
    }

    @Override
    public int hashCode() {
        // TODO: Implement proper hashCode method
        return 0;
    }

    @Override
    public String toString() {
        // TODO: Implement meaningful toString method
        return "";
    }
}
