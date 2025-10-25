package com.corporate.training.library.model;

import java.time.Duration;
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
      if (recordId == null || recordId.trim().isEmpty()) {
        throw new IllegalArgumentException("Record ID cannot be null or empty");
      }
        // - Student ID should not be null or empty
      if (studentId == null || studentId.trim().isEmpty()) {
        throw new IllegalArgumentException("Student ID cannot be null or empty");
      }
        // - Book ISBN should not be null or empty
      if (bookIsbn == null || bookIsbn.trim().isEmpty()) {
        throw new IllegalArgumentException("Book ISBN cannot be null or empty");
      }
        // - Borrow date should not be null
      if (borrowDate == null) {
        throw new IllegalArgumentException("Borrow date cannot be null");
      }
        // - Due date should not be null and should be after borrow date
      if (dueDate == null) {
          throw new IllegalArgumentException("Due date cannot be null");
      }
      if (dueDate.isBefore(borrowDate) || dueDate.isEqual(borrowDate)) {
        throw new IllegalArgumentException("Due date should be after borrow date");
      }
        this.recordId = recordId;
        this.studentId = studentId;
        this.bookIsbn = bookIsbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        // - Return date should be null initially
        this.returnDate = null;
        // - Status should be set to BORROWED
        this.status = BorrowStatus.BORROWED;
        // - Fine amount should be set to 0.0
        this.fineAmount = 0.0;
    }

    // Getters and Setters
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        // TODO: Add validation - Record ID should not be null or empty
      if (recordId == null || recordId.trim().isEmpty()) {
          throw new IllegalArgumentException("Record ID cannot be null or empty");
      }
      this.recordId = recordId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        // TODO: Add validation - Student ID should not be null or empty
      if (studentId == null || studentId.trim().isEmpty()) {
          throw new IllegalArgumentException("Student ID cannot be null or empty");
      }
      this.studentId = studentId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        // TODO: Add validation - Book ISBN should not be null or empty
      if (bookIsbn == null || bookIsbn.trim().isEmpty()) {
          throw new IllegalArgumentException("Book ISBN cannot be null or empty");
      }
      this.bookIsbn = bookIsbn;
    }

    public LocalDateTime getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDateTime borrowDate) {
        // TODO: Add validation - Borrow date should not be null
        if (borrowDate == null) {
            throw new IllegalArgumentException("Borrow date cannot be null");
        }
        this.borrowDate = borrowDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        // TODO: Add validation - Due date should not be null and should be after borrow date
      if (dueDate == null) {
          throw new IllegalArgumentException("Due date cannot be null");
      }
      if (this.borrowDate == null) {
          throw new IllegalStateException("Borrow date must be set before setting due date");
      }
      if (dueDate.isBefore(this.borrowDate) || dueDate.isEqual(this.borrowDate)) {
          throw new IllegalArgumentException("Due date should be after borrow date");
      }
      this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        // TODO: Add validation - Return date should not be null and should be after borrow date
        if (returnDate == null) {
          throw new IllegalArgumentException("Return date cannot be null");
        }
        if (this.borrowDate == null) {
            throw new IllegalStateException("Borrow date must be set before setting return date");
        }
        if (returnDate.isBefore(this.borrowDate) || returnDate.isEqual(this.borrowDate)) {
            throw new IllegalArgumentException("Return date should be after borrow date");
        }
        this.returnDate = returnDate;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        // TODO: Add validation - Status should not be null
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        // TODO: Add validation - Fine amount should not be negative
        if (fineAmount < 0) {
            throw new IllegalArgumentException("Fine amount cannot be negative");
        }
        this.fineAmount = fineAmount;
    }

    // Business methods
    public boolean isOverdue() {
        // TODO: Implement - return true if current date is after due date and book is not returned
      if (this.status == null || this.dueDate == null) {
        return false;
      }
      if (this.returnDate == null) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(this.dueDate);
      } else {
        return this.returnDate.isAfter(this.dueDate);
      }
    }

    public long getDaysOverdue() {
        // TODO: Implement - return number of days overdue (0 if not overdue)
      if (this.status == null || this.dueDate == null) {
        return 0;
      }
      if (this.status == BorrowStatus.RETURNED && this.returnDate != null && !this.returnDate.isAfter(this.dueDate)) {
        return 0;
      }

      LocalDateTime end;
      if (this.returnDate != null) {
        end = this.returnDate;
      } else {
        end = LocalDateTime.now();
      }

      if (end.isAfter(this.dueDate)) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(
          this.dueDate.toLocalDate(), end.toLocalDate());
        if (days > 0) {
          return days;
        }
        return 0;
      }
      return 0;
    }


  public void calculateFine(double dailyFineRate) {
        // TODO: Implement - calculate fine based on days overdue and daily fine rate
        // Only calculate if book is overdue and not returned
      if (dailyFineRate < 0) {
          throw new IllegalArgumentException("Daily fine rate cannot be negative");
      }
//      if (isOverdue()) {
      long daysOverdue = getDaysOverdue();
      this.fineAmount = daysOverdue * dailyFineRate;

//      } else {
//          this.fineAmount = 0.0;
//      }
    }

    public void returnBook(LocalDateTime returnDate) {
        // TODO: Implement - set return date, update status to RETURNED, and calculate final fine
        // Throw appropriate exception if book is already returned
      if (this.status == BorrowStatus.RETURNED) {
          throw new IllegalStateException("Book has already been returned");
      }
      setReturnDate(returnDate);
      calculateFine(1.0); //Assumed the rate
      this.status = BorrowStatus.RETURNED;


    }

    @Override
    public boolean equals(Object o) {
        // TODO: Implement proper equals method
        // Two borrow records are equal if they have the same record ID
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorrowRecord))  {
            return false;
        }
        BorrowRecord that = (BorrowRecord) o;
      if (this.recordId == null || that.recordId == null) {
        return false;
      }
      return this.recordId.equals(that.recordId);
    }

    @Override
    public int hashCode() {
        // TODO: Implement proper hashCode method
        return Objects.hash(recordId);
    }

    @Override
    public String toString() {
        // TODO: Implement meaningful toString method
        return "BorrowRecord{" +
                "recordId='" + recordId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status=" + status +
                ", fineAmount=" + fineAmount +
                '}';
    }
}
