package com.corporate.training.library.exception;

/**
 * Exception thrown when a student is not found in the library system.
 */
public class StudentNotFoundException extends LibraryException {
    
    private final String studentId;
    
    public StudentNotFoundException(String studentId) {
        // TODO: Implement constructor
        // - Create appropriate error message
        // - Set studentId field
        super("Student not found with ID: " + studentId, "STUDENT_NOT_FOUND");
        this.studentId = studentId;
    }
    
    public StudentNotFoundException(String studentId, Throwable cause) {
        // TODO: Implement constructor
        // - Create appropriate error message
        // - Set studentId field
        // - Pass cause to parent
        super("Student not found with ID: " + studentId, "STUDENT_NOT_FOUND", cause);
        this.studentId = studentId;
    }
    
    public String getStudentId() {
        return studentId;
    }
}
