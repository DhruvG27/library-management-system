package com.corporate.training.library.service;

/**
 * Statistics class for library operations.
 * Students need to implement proper getters, setters, and toString method.
 */
public class LibraryStatistics {
    private int totalBooks;
    private int availableBooks;
    private int borrowedBooks;
    private int totalStudents;
    private int activeStudents;
    private int totalBorrowRecords;
    private int activeBorrowRecords;
    private int overdueBooks;
    private double totalFines;
    private double collectedFines;

    // Default constructor
    public LibraryStatistics() {
    }

    // Constructor with all fields
    public LibraryStatistics(int totalBooks, int availableBooks, int borrowedBooks, 
                           int totalStudents, int activeStudents, int totalBorrowRecords, 
                           int activeBorrowRecords, int overdueBooks, double totalFines, 
                           double collectedFines) {
        // TODO: Implement constructor with validation
        // - All numeric values should be non-negative
        // - Set all fields
    }

    // Getters and Setters
    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        // TODO: Add validation - should be non-negative
        this.totalBooks = totalBooks;
    }

    public int getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(int availableBooks) {
        // TODO: Add validation - should be non-negative and not exceed total books
        this.availableBooks = availableBooks;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        // TODO: Add validation - should be non-negative and not exceed total books
        this.borrowedBooks = borrowedBooks;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        // TODO: Add validation - should be non-negative
        this.totalStudents = totalStudents;
    }

    public int getActiveStudents() {
        return activeStudents;
    }

    public void setActiveStudents(int activeStudents) {
        // TODO: Add validation - should be non-negative and not exceed total students
        this.activeStudents = activeStudents;
    }

    public int getTotalBorrowRecords() {
        return totalBorrowRecords;
    }

    public void setTotalBorrowRecords(int totalBorrowRecords) {
        // TODO: Add validation - should be non-negative
        this.totalBorrowRecords = totalBorrowRecords;
    }

    public int getActiveBorrowRecords() {
        return activeBorrowRecords;
    }

    public void setActiveBorrowRecords(int activeBorrowRecords) {
        // TODO: Add validation - should be non-negative and not exceed total borrow records
        this.activeBorrowRecords = activeBorrowRecords;
    }

    public int getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(int overdueBooks) {
        // TODO: Add validation - should be non-negative and not exceed active borrow records
        this.overdueBooks = overdueBooks;
    }

    public double getTotalFines() {
        return totalFines;
    }

    public void setTotalFines(double totalFines) {
        // TODO: Add validation - should be non-negative
        this.totalFines = totalFines;
    }

    public double getCollectedFines() {
        return collectedFines;
    }

    public void setCollectedFines(double collectedFines) {
        // TODO: Add validation - should be non-negative and not exceed total fines
        this.collectedFines = collectedFines;
    }

    // Business methods
    public double getOutstandingFines() {
        // TODO: Implement - return total fines minus collected fines
        return 0.0;
    }

    public double getBookUtilizationRate() {
        // TODO: Implement - return percentage of books that are borrowed
        // Formula: (borrowed books / total books) * 100
        return 0.0;
    }

    public double getStudentActivityRate() {
        // TODO: Implement - return percentage of students who are active
        // Formula: (active students / total students) * 100
        return 0.0;
    }

    public double getOverdueRate() {
        // TODO: Implement - return percentage of borrowed books that are overdue
        // Formula: (overdue books / active borrow records) * 100
        return 0.0;
    }

    @Override
    public String toString() {
        // TODO: Implement meaningful toString method
        // Should display all statistics in a formatted way
        return "";
    }
}
