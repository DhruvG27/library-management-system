package com.corporate.training.library.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Student entity representing a student in the library system.
 * Students need to implement proper equals, hashCode, and toString methods.
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String department;
    private int maxBooksAllowed;
    private int currentBooksBorrowed;
    private boolean isActive;

    // Default constructor
    public Student() {
    }

    // Constructor with essential fields
    public Student(String studentId, String firstName, String lastName, String email, String department) {
        // TODO: Implement constructor with validation
        // - Student ID should not be null or empty
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        } else {
            this.studentId = studentId;
        }
        // - First name should not be null or empty
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        } else {
            this.firstName = firstName;
        }
        // - Last name should not be null or empty
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        } else {
            this.lastName = lastName;
        }
        // - Email should not be null or empty and should be valid format
        if(email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email cannot be null or empty and must be valid");
        } else {
            this.email = email;
        }
        // - Department should not be null or empty
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        } else {
            this.department = department;
        }
        // - Max books allowed should be set to 5 by default
        this.maxBooksAllowed = 5;
        // - Current books borrowed should be set to 0
        this.currentBooksBorrowed = 0;
        // - isActive should be set to true
        this.isActive = true;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        // TODO: Add validation - Student ID should not be null or empty
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        // TODO: Add validation - First name should not be null or empty
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        // TODO: Add validation - Last name should not be null or empty
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // TODO: Add validation - Email should not be null or empty and should be valid format
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // TODO: Add validation - Phone number should be valid format (optional field)
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        // TODO: Add validation - Date of birth should not be null and should be in the past
        this.dateOfBirth = dateOfBirth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        // TODO: Add validation - Department should not be null or empty
        this.department = department;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        // TODO: Add validation - Max books allowed should be positive
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public int getCurrentBooksBorrowed() {
        return currentBooksBorrowed;
    }

    public void setCurrentBooksBorrowed(int currentBooksBorrowed) {
        // TODO: Add validation - Current books borrowed should not be negative
        // and should not exceed max books allowed
        this.currentBooksBorrowed = currentBooksBorrowed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    // Business methods
    public String getFullName() {
        // TODO: Implement - return concatenated first name and last name
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }

    public boolean canBorrowMoreBooks() {
        // TODO: Implement - return true if student is active and can borrow more books
        return isActive && (currentBooksBorrowed < maxBooksAllowed);
    }

    public void borrowBook() {
        // TODO: Implement - increment current books borrowed if student can borrow more
        // Throw appropriate exception if cannot borrow more books
        if(!isActive) {
            throw new IllegalStateException("Inactive student cannot borrow books");
        }
        if(currentBooksBorrowed >= maxBooksAllowed) {
            throw new IllegalStateException("Student has reached the maximum limit of borrowed books");
        }
        currentBooksBorrowed++;
    }

    public void returnBook() {
        // TODO: Implement - decrement current books borrowed if student has borrowed books
        // Throw appropriate exception if invalid operation
        if(currentBooksBorrowed <= 0) {
            throw new IllegalStateException("Student has no borrowed books to return");
        }
        currentBooksBorrowed--;
    }

    public int getRemainingBookLimit() {
        // TODO: Implement - return number of books student can still borrow
        return Math.max(0, maxBooksAllowed - currentBooksBorrowed);
    }

    @Override
    public boolean equals(Object o) {
        // TODO: Implement proper equals method
        // Two students are equal if they have the same student ID
        if (this == o) {
            return true;
        }
        if (! (o instanceof Student)) {
            return false;
        }
        Student that = (Student) o;
        return Objects.equals(this.studentId, that.getStudentId());
    }

    @Override
    public int hashCode() {
        // TODO: Implement proper hashCode method
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        // TODO: Implement meaningful toString method
        return "Student{" +
          "studentId='" + studentId + '\'' +
          ", firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          ", email='" + email + '\'' +
          ", department='" + department + '\'' +
          ", isActive=" + isActive +
          ", currentBooksBorrowed=" + currentBooksBorrowed +
          ", maxBooksAllowed=" + maxBooksAllowed +
          '}';
    }
}
