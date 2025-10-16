package com.corporate.training.library.exception;

/**
 * Exception thrown when a student tries to borrow more books than allowed.
 */
public class StudentLimitExceededException extends LibraryException {
    
    private final String studentId;
    private final int currentBorrowed;
    private final int maxAllowed;
    
    public StudentLimitExceededException(String studentId, int currentBorrowed, int maxAllowed) {
        // TODO: Implement constructor
        // - Create appropriate error message with current and max values
        // - Set studentId, currentBorrowed, and maxAllowed fields
        super("Student " + studentId + " has reached the borrowing limit. " +
              "Current: " + currentBorrowed + ", Max allowed: " + maxAllowed, 
              "STUDENT_LIMIT_EXCEEDED");
        this.studentId = studentId;
        this.currentBorrowed = currentBorrowed;
        this.maxAllowed = maxAllowed;
    }
    
    public StudentLimitExceededException(String studentId, int currentBorrowed, int maxAllowed, Throwable cause) {
        // TODO: Implement constructor
        // - Create appropriate error message with current and max values
        // - Set studentId, currentBorrowed, and maxAllowed fields
        // - Pass cause to parent
        super("Student " + studentId + " has reached the borrowing limit. " +
              "Current: " + currentBorrowed + ", Max allowed: " + maxAllowed, 
              "STUDENT_LIMIT_EXCEEDED", cause);
        this.studentId = studentId;
        this.currentBorrowed = currentBorrowed;
        this.maxAllowed = maxAllowed;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public int getCurrentBorrowed() {
        return currentBorrowed;
    }
    
    public int getMaxAllowed() {
        return maxAllowed;
    }
}
