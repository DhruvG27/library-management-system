package com.corporate.training.library.service;

import com.corporate.training.library.database.InMemoryDatabase;
import com.corporate.training.library.model.Book;
import com.corporate.training.library.model.BorrowRecord;
import com.corporate.training.library.model.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main service class for library operations.
 * Students need to implement thread-safe operations and proper exception handling.
 */
public class LibraryService {
    
    private final InMemoryDatabase database;
    private final ExecutorService executorService;
    
    // TODO: Initialize thread pool with appropriate size
    // Consider using Executors.newFixedThreadPool() or Executors.newCachedThreadPool()
    public LibraryService() {
        this.database = InMemoryDatabase.getInstance();
        this.executorService = Executors.newFixedThreadPool(10); // TODO: Configure appropriate pool size
    }
    
    // Book management operations
    public void addBook(Book book) {
        // TODO: Implement book addition with proper exception handling
        // - Validate book is not null
        // - Check if book with same ISBN already exists
        // - Add book to database
        // - Handle appropriate exceptions
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        try {
            if (database.getBook(book.getIsbn()) != null) {
                throw new RuntimeException("Book with ISBN " + book.getIsbn() + " already exists");
            }
            database.addBook(book);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add book: " + e.getMessage(), e);
        }
    }
    
    public Book getBook(String isbn) {
        // TODO: Implement book retrieval with proper exception handling
        // - Validate ISBN is not null or empty
        // - Retrieve book from database
        // - Handle case when book is not found
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        try {
            Book book = database.getBook(isbn);
            if (book == null) {
                throw new RuntimeException("Book with ISBN " + isbn + " not found");
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve book due to a database error: " + e.getMessage(), e);
        }
//        return null;
    }
    
    public List<Book> searchBooksByTitle(String title) {
        // TODO: Implement book search by title (case-insensitive, partial match)
        // - Validate title is not null or empty
        // - Search through all books
        // - Return matching books
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        String searchTerm = title.toLowerCase();
        List<Book> match = new ArrayList<>();
        List<Book> allBooks = database.getAllBooks();

        for (Book book : allBooks) {
            String bookTitle = book.getTitle();
            if (bookTitle != null && bookTitle.toLowerCase().contains(searchTerm)) {
                match.add(book);
            }
        }
        return match;
//        return null;
    }

    public Book getBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        try {
            Book book = database.getBookByTitle(title);
            if (book == null) {
                throw new RuntimeException("Book with title '" + title + "' not found");
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve book by title", e);
        }
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        // TODO: Implement book search by author (case-insensitive, partial match)
        // - Validate author is not null or empty
        // - Search through all books
        // - Return matching books
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        String searchTerm = author.toLowerCase();
        List<Book> match = new ArrayList<>();
        List<Book> allBooks = database.getAllBooks();

        for (Book book : allBooks) {
            String bookAuthor = book.getAuthor();
            if (bookAuthor != null && bookAuthor.toLowerCase().contains(searchTerm)) {
                match.add(book);
            }
        }
        return match;
//        return null;
    }
    
    public List<Book> getAvailableBooks() {
        // TODO: Implement retrieval of available books
        // - Get all books from database
        // - Filter books that are available (active and have available copies)
        // - Return list of available books
        List <Book> availableBooks = new ArrayList<>();
        List<Book> allBooks = database.getAllBooks();
        for (Book book : allBooks) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
//        return null;
    }
    
    // Student management operations
    public void addStudent(Student student) {
        // TODO: Implement student addition with proper exception handling
        // - Validate student is not null
        // - Check if student with same ID already exists
        // - Add student to database
        // - Handle appropriate exceptions
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (database.getStudent(student.getStudentId()) != null) {
            throw new RuntimeException("Student with ID " + student.getStudentId() + " already exists");
        }
        database.addStudent(student);
    }
    
    public Student getStudent(String studentId) {
        // TODO: Implement student retrieval with proper exception handling
        // - Validate student ID is not null or empty
        // - Retrieve student from database
        // - Handle case when student is not found

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        Student student = database.getStudent(studentId);
        if (student == null) {
            throw new RuntimeException("Student with ID " + studentId + " not found");
        }
        return student;
//        return null;
    }

    public List<Student> getStudentsByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        return database.getStudentsByDepartment(department);
    }
    
    public List<Student> searchStudentsByName(String name) {
        // TODO: Implement student search by name (case-insensitive, partial match)
        // - Validate name is not null or empty
        // - Search through all students
        // - Return matching students
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        String searchTerm = name.toLowerCase();
        List<Student> match = new ArrayList<>();
        List<Student> allStudents = database.getAllStudents();

        for (Student student : allStudents) {
            String firstName = student.getFirstName();
            String lastName = student.getLastName();

            String fullName = (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
            boolean firstNameMatches = firstName != null && firstName.toLowerCase().contains(searchTerm);
            boolean lastNameMatches = lastName != null && lastName.toLowerCase().contains(searchTerm);
            boolean fullNameMatches = fullName.toLowerCase().contains(searchTerm);

            if (firstNameMatches || lastNameMatches || fullNameMatches) {
                match.add(student);
            }

        }
        return match;
    }
    
    // Borrowing operations (these should be thread-safe)
    public synchronized BorrowRecord borrowBook(String studentId, String bookIsbn) throws SQLException {
        // TODO: Implement thread-safe book borrowing
        // - Validate student ID and book ISBN are not null or empty
        // - Check if student exists and is active
        // - Check if book exists and is available
        // - Check if student can borrow more books
        // - Create borrow record
        // - Update book available copies
        // - Update student current books borrowed
        // - Add borrow record to database
        // - Handle appropriate exceptions
        // - This method should be synchronized or use proper locking
      // validate input
      if (studentId == null || studentId.trim().isEmpty()) {
        throw new IllegalArgumentException("Student ID cannot be null or empty");
      }
      if (bookIsbn == null || bookIsbn.trim().isEmpty()) {
        throw new IllegalArgumentException("Book ISBN cannot be null or empty");
      }

      // load student
      Student student = database.getStudent(studentId);
      if (student == null) {
        throw new RuntimeException("Student with ID " + studentId + " not found");
      }
      if (!student.isActive()) {
        throw new RuntimeException("Student is not active");
      }
      if (!student.canBorrowMoreBooks()) {
        throw new RuntimeException("Student has reached borrowing limit");
      }

      // load book
      Book book;
      try {
        book = database.getBook(bookIsbn);
      } catch (SQLException e) {
        throw new RuntimeException("Failed to load book", e);
      }
      if (book == null) {
        throw new RuntimeException("Book with ISBN " + bookIsbn + " not found");
      }
      if (!book.isAvailable()) {
        throw new RuntimeException("Book is not available to borrow");
      }

      // student takes book
      student.borrowBook(); // this will throw if not allowed
      // book loses 1 copy
      book.borrowCopy();    // this will throw if not available

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime due = now.plusDays(14); // 2 week loan window

      String recordId = "REC-" + System.currentTimeMillis();

      BorrowRecord record = new BorrowRecord(
        recordId,
        studentId,
        bookIsbn,
        now,
        due
      );

      //    - update student row
      //    - update book row
      //    - insert new borrow record row
      boolean studentUpdated = database.updateStudent(student);
      if (!studentUpdated) {
        throw new RuntimeException("Failed to update student borrow count");
      }

      boolean bookUpdated = database.updateBook(book);
      if (!bookUpdated) {
        throw new RuntimeException("Failed to update book availability");
      }
      database.addBorrowRecord(record);
      return record;
    }

    public void returnBook(String recordId) {
        // TODO: Implement thread-safe book return
        // - Validate record ID is not null or empty
        // - Find borrow record
        // - Check if book is already returned
        // - Update borrow record with return date and status
        // - Update book available copies
        // - Update student current books borrowed
        // - Calculate fine if overdue
        // - Handle appropriate exceptions
        // - This method should be synchronized or use proper locking
        if (recordId == null || recordId.trim().isEmpty()) {
            throw new IllegalArgumentException("Record ID cannot be null or empty");
        }

      BorrowRecord record = database.getBorrowRecord(recordId);
      if (record == null) {
        throw new RuntimeException("Borrow record " + recordId + " not found");
      }

      if (record.getStatus() == com.corporate.training.library.model.BorrowStatus.RETURNED) {
        throw new RuntimeException("Book already returned for record " + recordId);
      }


      Student student = database.getStudent(record.getStudentId());
      if (student == null) {
        throw new RuntimeException("Student " + record.getStudentId() + " not found");
      }

      Book book;
      try {
        book = database.getBook(record.getBookIsbn());
      } catch (SQLException e) {
        throw new RuntimeException("Failed to load book for return", e);
      }
      if (book == null) {
        throw new RuntimeException("Book " + record.getBookIsbn() + " not found");
      }
      // student returns book
      student.returnBook();
      // library gets one more copy
      book.returnCopy();

      // mark record returned
      LocalDateTime now = LocalDateTime.now();
      record.returnBook(now);  // sets returnDate, status=RETURNED, also calculateFine(1.0)

      boolean studentUpdated = database.updateStudent(student);
      if (!studentUpdated) {
        throw new RuntimeException("Failed to update student after return");
      }

      boolean bookUpdated = database.updateBook(book);
      if (!bookUpdated) {
        throw new RuntimeException("Failed to update book copies after return");
      }

      boolean recordUpdated = database.updateBorrowRecord(record);
      if (!recordUpdated) {
        throw new RuntimeException("Failed to update borrow record as returned");
      }

    }
    
    public List<BorrowRecord> getStudentBorrowHistory(String studentId) {
        // TODO: Implement retrieval of student's borrow history
        // - Validate student ID is not null or empty
        // - Get all borrow records for the student
        // - Return list of borrow records
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        Student s = database.getStudent(studentId);
        if (s == null) {
            throw new RuntimeException("Student with ID " + studentId + " not found");
        }
        return database.getBorrowRecordsByStudent(studentId);
    }
    
    public List<BorrowRecord> getOverdueBooks() {
        // TODO: Implement retrieval of overdue books
        // - Get all borrow records from database
        // - Filter records that are overdue
        // - Return list of overdue records

        List<BorrowRecord> allRecords = database.getAllBorrowRecords();
        List<BorrowRecord> overdueRecords = new ArrayList<>();
        for (BorrowRecord record : allRecords) {
            if (record.isOverdue()) {
                overdueRecords.add(record);
            }
        }
        return overdueRecords;
    }
    
    // Asynchronous operations using CompletableFuture
    public CompletableFuture<List<Book>> searchBooksAsync(String searchTerm) {
        // TODO: Implement asynchronous book search
        // - Use CompletableFuture.supplyAsync() with executor service
        // - Search books by title and author concurrently
        // - Combine results and return unique books
        return CompletableFuture.supplyAsync(() -> {
            // TODO: Implement search logic
            return null;
        }, executorService);
    }
    
    public CompletableFuture<List<Student>> searchStudentsAsync(String searchTerm) {
        // TODO: Implement asynchronous student search
        // - Use CompletableFuture.supplyAsync() with executor service
        // - Search students by name and department concurrently
        // - Combine results and return unique students
        return CompletableFuture.supplyAsync(() -> {
            // TODO: Implement search logic
            return null;
        }, executorService);
    }
    
    public CompletableFuture<Void> processBulkBookReturns(List<String> recordIds) {
        // TODO: Implement asynchronous bulk book return processing
        // - Use CompletableFuture.runAsync() with executor service
        // - Process each return in parallel
        // - Handle exceptions for individual returns
        // - Log results
        return CompletableFuture.runAsync(() -> {
            // TODO: Implement bulk processing logic
        }, executorService);
    }
    
    // Fine calculation operations
    public double calculateFine(String recordId) {
        // TODO: Implement fine calculation
        // - Validate record ID is not null or empty
        // - Find borrow record
        // - Calculate fine based on days overdue
        // - Return calculated fine amount
      if (recordId == null || recordId.trim().isEmpty()) {
        throw new IllegalArgumentException("Record ID cannot be null or empty");
      }

      BorrowRecord record = database.getBorrowRecord(recordId);
      if (record == null) {
        throw new RuntimeException("Borrow record " + recordId + " not found");
      }

      // assume 1.0 per day fine
      record.calculateFine(1.0);

      // save new fine in dbase
      boolean updated = database.updateBorrowRecord(record);
      if (!updated) {
        throw new RuntimeException("Failed to update fine in record " + recordId);
      }

      return record.getFineAmount();
    }
    
    public List<BorrowRecord> getBooksWithFines() {
        // TODO: Implement retrieval of books with outstanding fines
        // - Get all borrow records
        // - Filter records that have fines
        // - Return list of records with fines
        List<BorrowRecord> allRecords = database.getAllBorrowRecords();
        List<BorrowRecord> recordsWithFines = new ArrayList<>();
        for (BorrowRecord record : allRecords) {
          record.calculateFine(1.0); // ensure fine is up to date
            if (record.getFineAmount() > 0.0) {
                recordsWithFines.add(record);
            }
        }
        return recordsWithFines;
    }
    
    // Statistics and reporting
    public LibraryStatistics getLibraryStatistics() {
        // TODO: Implement library statistics calculation
        // - Calculate total books, students, borrow records
        // - Calculate available books, borrowed books, overdue books
        // - Calculate total fines
        // - Return statistics object
        return null;
    }
    
    // Cleanup operations
    public void shutdown() {
        // TODO: Implement proper shutdown
        // - Shutdown executor service
        // - Wait for running tasks to complete
        // - Handle shutdown gracefully
        executorService.shutdown();
    }
}
