package com.corporate.training.library.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * BorrowRecord entity representing a book borrowing transaction.
 * Students need to implement proper equals, hashCode, and toString methods.
 */
public class BorrowRecord {
    private String recordId;
    private String studentId;
    private String bookIsbn;
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private BorrowStatus status;
    private double fineAmount;

    // Default constructor
    public BorrowRecord() {
    }

    // Constructor for new borrow record
    public BorrowRecord(String recordId, String studentId, String bookIsbn, LocalDateTime borrowDate, LocalDateTime dueDate) {
        // TODO: Implement constructor with validation
        // - Record ID should not be null or empty
        // - Student ID should not be null or empty
        // - Book ISBN should not be null or empty
        // - Borrow date should not be null
        // - Due date should not be null and should be after borrow date
        // - Return date should be null initially
        // - Status should be set to BORROWED
        // - Fine amount should be set to 0.0
    }

    // Getters and Setters
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        // TODO: Add validation - Record ID should not be null or empty
        this.recordId = recordId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        // TODO: Add validation - Student ID should not be null or empty
        this.studentId = studentId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        // TODO: Add validation - Book ISBN should not be null or empty
        this.bookIsbn = bookIsbn;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        // TODO: Add validation - Borrow date should not be null
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        // TODO: Add validation - Due date should not be null and should be after borrow date
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        // TODO: Add validation - Return date should not be null and should be after borrow date
        this.returnDate = returnDate;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        // TODO: Add validation - Status should not be null
        this.status = status;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        // TODO: Add validation - Fine amount should not be negative
        this.fineAmount = fineAmount;
    }

    // Business methods
    public boolean isOverdue() {
        // TODO: Implement - return true if current date is after due date and book is not returned
        return false;
    }

    public long getDaysOverdue() {
        // TODO: Implement - return number of days overdue (0 if not overdue)
        return 0;
    }

    public void calculateFine(double dailyFineRate) {
        // TODO: Implement - calculate fine based on days overdue and daily fine rate
        // Only calculate if book is overdue and not returned
    }

    public void returnBook(LocalDateTime returnDate) {
        // TODO: Implement - set return date, update status to RETURNED, and calculate final fine
        // Throw appropriate exception if book is already returned
    }

    @Override
    public boolean equals(Object o) {
        // TODO: Implement proper equals method
        // Two borrow records are equal if they have the same record ID
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
