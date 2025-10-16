package com.corporate.training.library.model;

/**
 * Enum representing the status of a borrow record.
 */
public enum BorrowStatus {
    BORROWED,    // Book is currently borrowed
    RETURNED,    // Book has been returned
    OVERDUE      // Book is overdue (borrowed but past due date)
}
