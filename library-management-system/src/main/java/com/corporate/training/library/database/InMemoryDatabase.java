package com.corporate.training.library.database;

import com.corporate.training.library.model.Book;
import com.corporate.training.library.model.BorrowRecord;
import com.corporate.training.library.model.Student;

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
        return null;
    }
    
    // Book operations
    public void addBook(Book book) {
        // TODO: Implement thread-safe book addition
        // - Acquire appropriate lock
        // - Validate book is not null
        // - Check if book with same ISBN already exists
        // - Add book to collection
        // - Release lock
    }
    
    public Book getBook(String isbn) {
        // TODO: Implement thread-safe book retrieval
        // - Acquire appropriate lock
        // - Return book by ISBN
        // - Release lock
        return null;
    }
    
    public List<Book> getAllBooks() {
        // TODO: Implement thread-safe retrieval of all books
        // - Acquire appropriate lock
        // - Return copy of all books to avoid external modification
        // - Release lock
        return new ArrayList<>();
    }
    
    public List<Book> getBooksByAuthor(String author) {
        // TODO: Implement thread-safe book search by author
        // - Acquire appropriate lock
        // - Filter books by author (case-insensitive)
        // - Return list of matching books
        // - Release lock
        return new ArrayList<>();
    }
    
    public List<Book> getBooksByCategory(String category) {
        // TODO: Implement thread-safe book search by category
        // - Acquire appropriate lock
        // - Filter books by category (case-insensitive)
        // - Return list of matching books
        // - Release lock
        return new ArrayList<>();
    }
    
    public boolean updateBook(Book book) {
        // TODO: Implement thread-safe book update
        // - Acquire appropriate lock
        // - Validate book is not null
        // - Check if book exists
        // - Update book in collection
        // - Release lock
        // - Return true if updated, false if book not found
        return false;
    }
    
    public boolean deleteBook(String isbn) {
        // TODO: Implement thread-safe book deletion
        // - Acquire appropriate lock
        // - Check if book exists and has no active borrow records
        // - Remove book from collection
        // - Release lock
        // - Return true if deleted, false if book not found or has active borrows
        return false;
    }
    
    // Student operations
    public void addStudent(Student student) {
        // TODO: Implement thread-safe student addition
        // - Acquire appropriate lock
        // - Validate student is not null
        // - Check if student with same ID already exists
        // - Add student to collection
        // - Release lock
    }
    
    public Student getStudent(String studentId) {
        // TODO: Implement thread-safe student retrieval
        // - Acquire appropriate lock
        // - Return student by ID
        // - Release lock
        return null;
    }
    
    public List<Student> getAllStudents() {
        // TODO: Implement thread-safe retrieval of all students
        // - Acquire appropriate lock
        // - Return copy of all students to avoid external modification
        // - Release lock
        return new ArrayList<>();
    }
    
    public List<Student> getStudentsByDepartment(String department) {
        // TODO: Implement thread-safe student search by department
        // - Acquire appropriate lock
        // - Filter students by department (case-insensitive)
        // - Return list of matching students
        // - Release lock
        return new ArrayList<>();
    }
    
    public boolean updateStudent(Student student) {
        // TODO: Implement thread-safe student update
        // - Acquire appropriate lock
        // - Validate student is not null
        // - Check if student exists
        // - Update student in collection
        // - Release lock
        // - Return true if updated, false if student not found
        return false;
    }
    
    public boolean deleteStudent(String studentId) {
        // TODO: Implement thread-safe student deletion
        // - Acquire appropriate lock
        // - Check if student exists and has no active borrow records
        // - Remove student from collection
        // - Release lock
        // - Return true if deleted, false if student not found or has active borrows
        return false;
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
    }
    
    public int getTotalBooksCount() {
        // TODO: Implement thread-safe count of total books
        return 0;
    }
    
    public int getTotalStudentsCount() {
        // TODO: Implement thread-safe count of total students
        return 0;
    }
    
    public int getTotalBorrowRecordsCount() {
        // TODO: Implement thread-safe count of total borrow records
        return 0;
    }
}
