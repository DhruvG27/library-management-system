package com.corporate.training.library.database;

import java.sql.*;

import com.corporate.training.library.model.Book;
import com.corporate.training.library.model.BorrowRecord;
import com.corporate.training.library.model.Student;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * In-memory database implementation for the library management system.
 * Students need to implement thread-safe operations using appropriate collections and synchronization.
 */
public class InMemoryDatabase {

    // TODO: Choose appropriate thread-safe collections
    // Consider using ConcurrentHashMap, Collections.synchronizedMap(), or other thread-safe alternatives
    private final Map<String, Book> books;
    private final Map<String, Student> students;
    private final Map<String, BorrowRecord> borrowRecords;

    // TODO: Implement proper locking mechanism for thread safety
    // Consider using ReadWriteLock for better performance
    private final ReadWriteLock booksLock;
    private final ReadWriteLock studentsLock;
    private final ReadWriteLock borrowRecordsLock;

    // Singleton instance
    private static volatile InMemoryDatabase instance;

    private InMemoryDatabase() {
        // TODO: Initialize collections with appropriate thread-safe implementations
        this.books = new ConcurrentHashMap<>();
        this.students = new ConcurrentHashMap<>();
        this.borrowRecords = new ConcurrentHashMap<>();

        // TODO: Initialize locks
        this.booksLock = new ReentrantReadWriteLock();
        this.studentsLock = new ReentrantReadWriteLock();
        this.borrowRecordsLock = new ReentrantReadWriteLock();
    }

    /**
     * Get singleton instance of the database
     * TODO: Implement thread-safe singleton pattern
     */
    public static InMemoryDatabase getInstance() {
        // TODO: Implement double-checked locking or use other thread-safe singleton pattern
        InMemoryDatabase local = instance;
        if (local == null) {
            synchronized (InMemoryDatabase.class) {
                local = instance;
                if (local == null) {
                    local = new InMemoryDatabase();
                    instance = local;
                }
            }
        }
        return local;
    }

    // Book operations
    public void addBook(Book book) throws SQLException {
        // TODO: Implement thread-safe book addition
        // - Acquire appropriate lock
        // - Validate book is not null
        // - Check if book with same ISBN already exists
        // - Add book to collection
        // - Release lock
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Book or ISBN cannot be null or empty");
        }

        String sql = """
        INSERT INTO BOOKS(ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getCategory());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getAvailableCopies());
            ps.setDate(7, book.getPublishedDate() == null ? null : java.sql.Date.valueOf(book.getPublishedDate()));
            ps.setBoolean(8, book.isActive());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add book", e);
        }
    }

    public Book getBook(String isbn) throws SQLException {
        // TODO: Implement thread-safe book retrieval
        // - Acquire appropriate lock
        // - Return book by ISBN
        // - Release lock
        if (isbn == null || isbn.trim().isEmpty()) return null;

        String sql = """
        SELECT ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE
        FROM PUBLIC.BOOKS WHERE ISBN = ?
    """;

        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapBook(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get book", e);
        }
    }

    public Book getBookByTitle(String title) throws SQLException {
        if (title == null || title.trim().isEmpty()) return null;
        String sql = """
        SELECT ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE
        FROM PUBLIC.BOOKS WHERE TITLE = ?
        """;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapBook(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get book by title", e);
        }


    }

    public List<Book> getAllBooks() {
        // TODO: Implement thread-safe retrieval of all books
        // - Acquire appropriate lock
        // - Return copy of all books to avoid external modification
        // - Release lock

        String sql = """
            SELECT ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE
            FROM PUBLIC.BOOKS ORDER BY TITLE""";

        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(mapBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to Get All Books", e);
        }
//        return new ArrayList<>();
    }

    public List<Book> getBooksByAuthor(String author) {
        // TODO: Implement thread-safe book search by author
        // - Acquire appropriate lock
        // - Filter books by author (case-insensitive)
        // - Return list of matching books
        // - Release lock
        if (author == null|| author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        String sql = """
            SELECT ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE
            FROM PUBLIC.BOOKS WHERE LOWER(AUTHOR) LIKE LOWER(?) ORDER BY TITLE""";
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + author + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Book> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapBook(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search by author", e);
        }
//        return new ArrayList<>();
    }

    public List<Book> getBooksByCategory(String category) {
        // TODO: Implement thread-safe book search by category
        // - Acquire appropriate lock
        // - Filter books by category (case-insensitive)
        // - Return list of matching books
        // - Release lock
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        String sql = """
            SELECT ISBN, TITLE, AUTHOR, CATEGORY, TOTAL_COPIES, AVAILABLE_COPIES, PUBLISHED_DATE, ACTIVE
            FROM PUBLIC.BOOKS WHERE LOWER(CATEGORY) LIKE LOWER(?) ORDER BY TITLE""";
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + category + "%");

            try (ResultSet rs = ps.executeQuery()) {
                List<Book> book = new ArrayList<>();
                while (rs.next()) {
                    book.add(mapBook(rs));
                }
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search by category", e);
        }
//        return new ArrayList<>();
    }

    public boolean updateBook(Book book) {
        // TODO: Implement thread-safe book update
        // - Acquire appropriate lock
        // - Validate book is not null
        // - Check if book exists
        // - Update book in collection
        // - Release lock
        // - Return true if updated, false if book not found
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            return false;
        }

        String sql = """
                UPDATE PUBLIC.BOOKS
                SET TITLE=?, AUTHOR=?, CATEGORY=?, TOTAL_COPIES=?, AVAILABLE_COPIES=?, PUBLISHED_DATE=?, ACTIVE=?
                WHERE ISBN=?
                """;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());
            if (book.getPublishedDate() == null) {
                ps.setNull(6, java.sql.Types.DATE);
            } else {
                ps.setDate(6, java.sql.Date.valueOf(book.getPublishedDate()));
            }
            ps.setBoolean(7, book.isActive());
            ps.setString(8, book.getIsbn());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteBook(String isbn) {
        // TODO: Implement thread-safe book deletion
        // - Acquire appropriate lock
        // - Check if book exists and has no active borrow records
        // - Remove book from collection
        // - Release lock
        // - Return true if deleted, false if book not found or has active borrows
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM PUBLIC.BOOKS WHERE ISBN=?")) {
            ps.setString(1, isbn);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setIsbn(rs.getString("ISBN"));
        book.setTitle(rs.getString("TITLE"));
        book.setAuthor(rs.getString("AUTHOR"));
        book.setCategory(rs.getString("CATEGORY"));
        book.setTotalCopies(rs.getInt("TOTAL_COPIES"));
        book.setAvailableCopies(rs.getInt("AVAILABLE_COPIES"));
        java.sql.Date pubDate = rs.getDate("PUBLISHED_DATE");
        book.setPublishedDate(pubDate == null ? null : pubDate.toLocalDate());
        book.setActive(rs.getBoolean("ACTIVE"));
        return book;
    }

    // Student operations
    public void addStudent(Student student) {
        // TODO: Implement thread-safe student addition
        // - Acquire appropriate lock
        // - Validate student is not null
        // - Check if student with same ID already exists
        // - Add student to collection
        // - Release lock
        if (student == null || student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("Student or Student ID cannot be null or empty");
        }

        String sql = """
          INSERT INTO PUBLIC.STUDENTS
          (ID, FIRST_NAME, LAST_NAME, EMAIL, DEPARTMENT, PHONE_NUMBER, MAX_BOOKS_ALLOWED, CURRENT_BOOKS_BORROWED, ACTIVE)
          VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getStudentId());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getDepartment());
            ps.setString(6, student.getPhoneNumber());
            ps.setInt(7, student.getMaxBooksAllowed());
            ps.setInt(8, student.getCurrentBooksBorrowed());
            ps.setBoolean(9, student.isActive());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student", e);
        }

    }

    public Student getStudent(String studentId) {
        // TODO: Implement thread-safe student retrieval
        // - Acquire appropriate lock
        // - Return student by ID
        // - Release lock
        if (studentId == null || studentId.trim().isEmpty()) {
            return null;
        }
        String sql = """
        SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, DEPARTMENT, PHONE_NUMBER,
               MAX_BOOKS_ALLOWED, CURRENT_BOOKS_BORROWED, ACTIVE
        FROM PUBLIC.STUDENTS WHERE ID = ?
        """;

        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get student", e);
        }
        return null;
    }

    public List<Student> getAllStudents() {
        // TODO: Implement thread-safe retrieval of all students
        // - Acquire appropriate lock
        // - Return copy of all students to avoid external modification
        // - Release lock
        String sql = """
        SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, DEPARTMENT, PHONE_NUMBER,
               MAX_BOOKS_ALLOWED, CURRENT_BOOKS_BORROWED, ACTIVE
        FROM PUBLIC.STUDENTS ORDER BY FIRST_NAME, LAST_NAME
        """;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                students.add(mapStudent(rs));
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to Get All Students", e);
        }
//        return new ArrayList<>();
    }

    public List<Student> getStudentsByDepartment(String department) {
        // TODO: Implement thread-safe student search by department
        // - Acquire appropriate lock
        // - Filter students by department (case-insensitive)
        // - Return list of matching students
        // - Release lock
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }

        String sql = """
        SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, DEPARTMENT, PHONE_NUMBER,
               MAX_BOOKS_ALLOWED, CURRENT_BOOKS_BORROWED, ACTIVE
        FROM PUBLIC.STUDENTS
        WHERE LOWER(DEPARTMENT) LIKE LOWER(?)
        ORDER BY FIRST_NAME, LAST_NAME
        """;

        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + department + "%");

            try (ResultSet rs = ps.executeQuery()) {
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
                return students;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to Get Students by Department", e);
        }
//        return new ArrayList<>();
    }

    public boolean updateStudent(Student student) {
        // TODO: Implement thread-safe student update
        // - Acquire appropriate lock
        // - Validate student is not null
        // - Check if student exists
        // - Update student in collection
        // - Release lock
        // - Return true if updated, false if student not found
        if (student == null || student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return false;
        }
        String sql = """
                UPDATE PUBLIC.STUDENTS
                SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, DEPARTMENT=?, PHONE_NUMBER=?, ACTIVE=?, CURRENT_BOOKS_BORROWED=?
                WHERE ID=?
                """;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getDepartment());
            ps.setString(5, student.getPhoneNumber());
            ps.setBoolean(6, student.isActive());
            ps.setInt(7, student.getCurrentBooksBorrowed());
            ps.setString(8, student.getStudentId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
//        return false;
    }

    public boolean deleteStudent(String studentId) {
        // TODO: Implement thread-safe student deletion
        // - Acquire appropriate lock
        // - Check if student exists and has no active borrow records
        // - Remove student from collection
        // - Release lock
        // - Return true if deleted, false if student not found or has active borrows
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        if (unReturnedBorrowedBooksExistForStudent(studentId)) {
            return false;
        }
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM PUBLIC.STUDENTS WHERE ID=?")) {
            ps.setString(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
//        return false;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
      Student student = new Student();
      student.setStudentId(rs.getString("ID"));
      student.setFirstName(rs.getString("FIRST_NAME"));
      student.setLastName(rs.getString("LAST_NAME"));
      student.setEmail(rs.getString("EMAIL"));
      student.setDepartment(rs.getString("DEPARTMENT"));
      student.setPhoneNumber(rs.getString("PHONE_NUMBER"));
      student.setMaxBooksAllowed(rs.getInt("MAX_BOOKS_ALLOWED"));
      student.setCurrentBooksBorrowed(rs.getInt("CURRENT_BOOKS_BORROWED"));
      student.setActive(rs.getBoolean("ACTIVE"));
      return student;
    }

    private boolean unReturnedBorrowedBooksExistForStudent(String studentId) {
        String sql = """
          SELECT COUNT(*) FROM PUBLIC.BORROW_RECORDS
          WHERE STUDENT_ID = ? AND RETURN_DATETIME IS NULL""";
        try (Connection c = H2.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check unreturned borrows", e);
        }
    }

    // Borrow record operations
    public void addBorrowRecord(BorrowRecord record) {
        // TODO: Implement thread-safe borrow record addition
        // - Acquire appropriate lock
        // - Validate record is not null
        // - Check if record with same ID already exists
        // - Add record to collection
        // - Release lock
    }

    public BorrowRecord getBorrowRecord(String recordId) {
        // TODO: Implement thread-safe borrow record retrieval
        // - Acquire appropriate lock
        // - Return record by ID
        // - Release lock
        return null;
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        // TODO: Implement thread-safe retrieval of all borrow records
        // - Acquire appropriate lock
        // - Return copy of all records to avoid external modification
        // - Release lock
        return new ArrayList<>();
    }

    public List<BorrowRecord> getBorrowRecordsByStudent(String studentId) {
        // TODO: Implement thread-safe borrow record search by student
        // - Acquire appropriate lock
        // - Filter records by student ID
        // - Return list of matching records
        // - Release lock
        return new ArrayList<>();
    }

    public List<BorrowRecord> getBorrowRecordsByBook(String bookIsbn) {
        // TODO: Implement thread-safe borrow record search by book
        // - Acquire appropriate lock
        // - Filter records by book ISBN
        // - Return list of matching records
        // - Release lock
        return new ArrayList<>();
    }

    public List<BorrowRecord> getOverdueRecords() {
        // TODO: Implement thread-safe retrieval of overdue records
        // - Acquire appropriate lock
        // - Filter records that are overdue
        // - Return list of overdue records
        // - Release lock
        return new ArrayList<>();
    }

    public boolean updateBorrowRecord(BorrowRecord record) {
        // TODO: Implement thread-safe borrow record update
        // - Acquire appropriate lock
        // - Validate record is not null
        // - Check if record exists
        // - Update record in collection
        // - Release lock
        // - Return true if updated, false if record not found
        return false;
    }

    // Utility methods
    public void clearAllData() {
        // TODO: Implement thread-safe data clearing
        // - Acquire all locks
        // - Clear all collections
        // - Release all locks
        // Note: This method should be used only for testing
        try (Connection conn = H2.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM BORROW_RECORDS");
            stmt.executeUpdate("DELETE FROM STUDENTS");
            stmt.executeUpdate("DELETE FROM BOOKS");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to Clear Data", e);
        }
    }

    public int getTotalBooksCount() {
        // TODO: Implement thread-safe count of total books
//        return 0;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM BOOKS");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getTotalStudentsCount() {
        // TODO: Implement thread-safe count of total students
//        return 0;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM STUDENTS");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int getTotalBorrowRecordsCount() {
        // TODO: Implement thread-safe count of total borrow records
//        return 0;
        try (Connection conn = H2.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM BORROW_RECORDS");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

