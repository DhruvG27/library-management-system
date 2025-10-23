package com.corporate.training.library.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import com.corporate.training.library.model.Book;
import com.corporate.training.library.model.BorrowRecord;
import com.corporate.training.library.model.Student;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InMemoryDatabase.
 * Students need to implement comprehensive test cases for all database functionality.
 */
@DisplayName("In Memory Database Tests")
class InMemoryDatabaseTest {

    private InMemoryDatabase database;
    private Book testBook;
    private Student testStudent;
    private BorrowRecord testBorrowRecord;

    @BeforeEach
    void setUp() {
        // TODO: Initialize database and test data
        try {
            dropAll();
            createSchema();
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up database schema", e);
        }
        database = InMemoryDatabase.getInstance();
        database.clearAllData();

        testBook = new Book("978-0-123456-78-9", "Test Book", "Test Author", "Test Category", 3);
        testStudent = new Student("STU001", "John", "Doe", "john.doe@email.com", "Computer Science");
        testBorrowRecord = new BorrowRecord("REC001", "STU001", "978-0-123456-78-9",
            LocalDateTime.now(), LocalDateTime.now().plusDays(14));
    }

    private void dropAll() throws Exception {
        try (Connection conn = H2.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");

            stmt.execute("DROP TABLE IF EXISTS PUBLIC.BORROW_RECORDS");
            stmt.execute("DROP TABLE IF EXISTS PUBLIC.STUDENTS");
            stmt.execute("DROP TABLE IF EXISTS PUBLIC.BOOKS");

            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (Exception e) {
            throw new Exception("Error dropping tables", e);
        }
    }
    private void createSchema() {
        try (Connection conn = H2.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.BOOKS (
                  ISBN VARCHAR(50) PRIMARY KEY,
                  TITLE VARCHAR(255) NOT NULL,
                  AUTHOR VARCHAR(255),
                  CATEGORY VARCHAR(100),
                  TOTAL_COPIES INT NOT NULL,
                  AVAILABLE_COPIES INT NOT NULL,
                  PUBLISHED_DATE DATE,
                  ACTIVE BOOLEAN NOT NULL
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.STUDENTS (
                  ID VARCHAR(50) PRIMARY KEY,
                  FIRST_NAME VARCHAR(255) NOT NULL,
                  LAST_NAME VARCHAR(255),
                  EMAIL VARCHAR(255),
                  DEPARTMENT VARCHAR(100),
                  PHONE_NUMBER VARCHAR(50),
                  MAX_BOOKS_ALLOWED INT NOT NULL,
                  CURRENT_BOOKS_BORROWED INT NOT NULL,
                  ACTIVE BOOLEAN NOT NULL
                )
            """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS PUBLIC.BORROW_RECORDS (
                  ID VARCHAR(50) PRIMARY KEY,
                  STUDENT_ID VARCHAR(50) NOT NULL,
                  BOOK_ISBN VARCHAR(50) NOT NULL,
                  BORROW_DATETIME TIMESTAMP NOT NULL,
                  DUE_DATETIME TIMESTAMP NOT NULL,
                  RETURN_DATETIME TIMESTAMP NULL,
                  STATUS VARCHAR(30) NOT NULL,
                  FINE DOUBLE PRECISION NOT NULL,
                  CONSTRAINT FK_BR_STUDENT FOREIGN KEY (STUDENT_ID) REFERENCES PUBLIC.STUDENTS(ID),
                  CONSTRAINT FK_BR_BOOK FOREIGN KEY (BOOK_ISBN) REFERENCES PUBLIC.BOOKS(ISBN)
                )
            """);
        } catch (Exception e) {
            throw new RuntimeException("Error creating tables", e);
        }
    }

    @Nested
    @DisplayName("Singleton Tests")
    class SingletonTests {

        @Test
        @DisplayName("Should return same instance")
        void shouldReturnSameInstance() {
            // TODO: Test singleton pattern
            // - Get multiple instances
            // - Verify they are the same instance
            InMemoryDatabase instance1 = InMemoryDatabase.getInstance();
            InMemoryDatabase instance2 = InMemoryDatabase.getInstance();
            assertThat(instance1).isSameAs(instance2);
        }

        @Test
        @DisplayName("Should be thread-safe singleton")
        void shouldBeThreadSafeSingleton() throws InterruptedException {
            // TODO: Test thread-safe singleton
            // - Create multiple threads getting instance
            // - Verify all threads get same instance
            // - Verify no race conditions occur
        }
    }

    @Nested
    @DisplayName("Book Operations Tests")
    class BookOperationsTests {

        @Test
        @DisplayName("Should add book successfully")
        void shouldAddBookSuccessfully() throws SQLException {
            // TODO: Test adding a book
            // - Add book to database
            // - Verify book is added
            // - Verify book can be retrieved
            database.addBook(testBook);

            assertThat(database.getTotalBooksCount()).isEqualTo(1);

            Book book = database.getBook(testBook.getIsbn());
            assertThat(book).isNotNull();
            assertThat(book.getTitle()).isEqualTo("Test Book");
            assertThat(book.getAuthor()).isEqualTo("Test Author");
            assertThat(book.getCategory()).isEqualTo("Test Category");
            assertThat(book.getTotalCopies()).isEqualTo(3);
            assertThat(book.getAvailableCopies()).isEqualTo(3);
        }

        @Test
        @DisplayName("Should throw exception when adding null book")
        void shouldThrowExceptionWhenAddingNullBook() {
            // TODO: Test adding null book
            // - Attempt to add null book
            // - Verify appropriate exception is thrown
            try {
                database.addBook(null);
                Assertions.fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException expected) {
                // ok
            } catch (Exception other) {
                Assertions.fail("Unexpected exception: " + other);
            }
        }

        @Test
        @DisplayName("Should throw exception when adding duplicate book")
        void shouldThrowExceptionWhenAddingDuplicateBook() throws SQLException {
            // TODO: Test adding duplicate book
            // - Add a book
            // - Attempt to add another book with same ISBN
            // - Verify appropriate exception is thrown
            database.addBook(testBook);
            try {
                database.addBook(new Book(testBook.getIsbn(), "X", "Y", "Z", 1));
                Assertions.fail("Expected runtime exception for duplicate ISBN");
            } catch (RuntimeException expected) {
                assertThat(expected.getMessage()).contains("Book with ISBN " +testBook.getIsbn() +" already exists");
            }
        }

        @Test
        @DisplayName("Should retrieve book by ISBN")
        void shouldRetrieveBookByIsbn() throws SQLException {
            // TODO: Test retrieving book by ISBN
            // - Add a book
            // - Retrieve book by ISBN
            // - Verify correct book is returned
            database.addBook(testBook);
            Book book = database.getBook("978-0-123456-78-9");
            assertThat(book).isNotNull();
            assertThat(book.getIsbn()).isEqualTo(testBook.getIsbn());
        }

        @Test
        @DisplayName("Should return null for non-existent book")
        void shouldReturnNullForNonExistentBook() throws SQLException {
            // TODO: Test retrieving non-existent book
            // - Attempt to retrieve book with non-existent ISBN
            // - Verify null is returned
            Book book = database.getBook("DOES-NOT-EXIST");
            assertThat(book).isNull();
        }

        @Test
        @DisplayName("Should get all books")
        void shouldGetAllBooks() throws SQLException {
            // TODO: Test getting all books
            // - Add multiple books
            // - Get all books
            // - Verify all books are returned
            database.addBook(testBook);
            database.addBook(new Book("978-1-234567-89-0", "Another Book", "Another Author", "Another Category", 2));
            database.addBook(new Book("978-2-345678-90-1", "Third Book", "Third Author", "Third Category", 5));
            database.addBook(new Book("978-3-456789-01-2", "Fourth Book", "Fourth Author", "Fourth Category", 4));

            List<Book> books = database.getAllBooks();
            assertThat(books).hasSize(4);
            assertThat(books).extracting(Book::getIsbn)
                .containsExactlyInAnyOrder("978-0-123456-78-9", "978-1-234567-89-0", "978-2-345678-90-1", "978-3-456789-01-2");
        }

        @Test
        @DisplayName("Should search books by author")
        void shouldSearchBooksByAuthor() throws SQLException {
            // TODO: Test searching books by author
            // - Add multiple books with different authors
            // - Search by author
            // - Verify correct books are returned
            database.addBook(testBook);
            database.addBook(new Book("978-1-234567-89-0", "Another Book", "Second Author", "Another Category", 2));
            database.addBook(new Book("978-2-345678-90-1", "Third Book", "Third Author", "Third Category", 5));
            database.addBook(new Book("978-3-456789-01-2", "Fourth Book", "Second Author", "Fourth Category", 4));
            database.addBook(new Book("978-4-567890-12-3", "Fifth Book", "Fifth Author", "Fifth Category", 1));
            List<Book> booksByAuthor = database.getBooksByAuthor("Second Author");
            assertThat(booksByAuthor).hasSize(2);
            assertThat(booksByAuthor).extracting(Book::getIsbn)
                .containsExactlyInAnyOrder("978-1-234567-89-0", "978-3-456789-01-2");
        }

        @Test
        @DisplayName("Should search books by category")
        void shouldSearchBooksByCategory() throws SQLException {
            // TODO: Test searching books by category
            // - Add multiple books with different categories
            // - Search by category
            // - Verify correct books are returned
            database.addBook(testBook);
            database.addBook(new Book("978-1-234567-89-0", "Another Book", "Another Author", "Science Fiction", 2));
            database.addBook(new Book("978-2-345678-90-1", "Third Book", "Third Author", "Fantasy", 5));
            database.addBook(new Book("978-3-456789-01-2", "Fourth Book", "Fourth Author", "Science Fiction", 4));
            database.addBook(new Book("978-4-567890-12-3", "Fifth Book", "Fifth Author", "History", 1));

            List<Book> booksByCategory = database.getBooksByCategory("Science Fiction");
            assertThat(booksByCategory).hasSize(2);
            assertThat(booksByCategory)
                .extracting(Book::getIsbn)
                .containsExactlyInAnyOrder("978-1-234567-89-0", "978-3-456789-01-2");
        }

        @Test
        @DisplayName("Should update book successfully")
        void shouldUpdateBookSuccessfully() throws SQLException {
            // TODO: Test updating a book
            // - Add a book
            // - Update book details
            // - Verify book is updated
            database.addBook(testBook);

            Book updatedBook = database.getBook(testBook.getIsbn());
            updatedBook.setTitle("I Don't Know what to Do");
            updatedBook.setAuthor("Jane Doe");
            updatedBook.setCategory("Mystery");
            updatedBook.setTotalCopies(5);

            boolean isUpdated = database.updateBook(updatedBook);
            assertThat(isUpdated).isTrue();

            Book afterUpdate = database.getBook(testBook.getIsbn());
            assertThat(afterUpdate.getTitle()).isEqualTo("I Don't Know what to Do");
            assertThat(afterUpdate.getAuthor()).isEqualTo("Jane Doe");
            assertThat(afterUpdate.getCategory()).isEqualTo("Mystery");
            assertThat(afterUpdate.getTotalCopies()).isEqualTo(5);


        }

        @Test
        @DisplayName("Should return false when updating non-existent book")
        void shouldReturnFalseWhenUpdatingNonExistentBook() {
            // TODO: Test updating non-existent book
            // - Attempt to update non-existent book
            // - Verify false is returned
            Book nonExistentBook = new Book("DOES-NOT-EXIST", "Non Existent", "No Author", "No Category", 1);
            boolean isUpdated = database.updateBook(nonExistentBook);
            assertThat(isUpdated).isFalse();
        }

        @Test
        @DisplayName("Should delete book successfully")
        void shouldDeleteBookSuccessfully() throws SQLException {
            // TODO: Test deleting a book
            // - Add a book
            // - Delete book
            // - Verify book is deleted
            database.addBook(testBook);
            boolean isDeleted = database.deleteBook(testBook.getIsbn());
            assertThat(isDeleted).isTrue();
            assertThat(database.getBook(testBook.getIsbn())).isNull();
            assertThat(database.getTotalBooksCount()).isZero();
        }

        @Test
        @DisplayName("Should return false when deleting non-existent book")
        void shouldReturnFalseWhenDeletingNonExistentBook() {
            // TODO: Test deleting non-existent book
            // - Attempt to delete non-existent book
            // - Verify false is returned
            boolean isDeleted = database.deleteBook("Is Missing");
            assertThat(isDeleted).isFalse();
        }
    }

    @Nested
    @DisplayName("Student Operations Tests")
    class StudentOperationsTests {

        @Test
        @DisplayName("Should add student successfully")
        void shouldAddStudentSuccessfully() {
            // TODO: Test adding a student
            // - Add student to database
            // - Verify student is added
            // - Verify student can be retrieved
            Student student = new Student("STU100", "Alice", "Wong", "alice@u.edu", "Physics");
            student.setPhoneNumber("999-000-1111");
            student.setMaxBooksAllowed(5);
            student.setCurrentBooksBorrowed(0);
            student.setActive(true);

            database.addStudent(student);

            Student studentFromDb = database.getStudent("STU100");
            assertThat(studentFromDb).isNotNull();
            assertThat(studentFromDb.getFirstName()).isEqualTo("Alice");
            assertThat(studentFromDb.getLastName()).isEqualTo("Wong");
            assertThat(studentFromDb.getDepartment()).isEqualTo("Physics");

        }

        @Test
        @DisplayName("Should throw exception when adding null student")
        void shouldThrowExceptionWhenAddingNullStudent() {
            // TODO: Test adding null student
            // - Attempt to add null student
            // - Verify appropriate exception is thrown
            try {
                database.addStudent(null);
                Assertions.fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException expected) {
                assertThat(expected.getMessage()).contains("Student or Student ID cannot be null or empty");
            }
        }


        @Test
        @DisplayName("Should throw exception when adding duplicate student")
        void shouldThrowExceptionWhenAddingDuplicateStudent() {
            // TODO: Test adding duplicate student
            // - Add a student
            // - Attempt to add another student with same ID
            // - Verify appropriate exception is thrown
            Student stu = new Student("S1","A","B","a@b.com","CS");
            database.addStudent(stu);
            Student stu2 = new Student("S1", "B", "C", "x@y.com", "Math");
            try {
                database.addStudent(stu2);
                Assertions.fail("Expected runtime exception for duplicate ID");
            } catch (RuntimeException expected) {
                String msg = expected.getMessage();
                assertNotNull(msg);
                assertTrue(msg.contains("Failed to add student as ID already exists"));
            }

        }

        @Test
        @DisplayName("Should retrieve student by ID")
        void shouldRetrieveStudentById() {
            // TODO: Test retrieving student by ID
            // - Add a student
            // - Retrieve student by ID
            // - Verify correct student is returned
        }

        @Test
        @DisplayName("Should return null for non-existent student")
        void shouldReturnNullForNonExistentStudent() {
            // TODO: Test retrieving non-existent student
            // - Attempt to retrieve student with non-existent ID
            // - Verify null is returned
        }

        @Test
        @DisplayName("Should get all students")
        void shouldGetAllStudents() {
            // TODO: Test getting all students
            // - Add multiple students
            // - Get all students
            // - Verify all students are returned
        }

        @Test
        @DisplayName("Should search students by department")
        void shouldSearchStudentsByDepartment() {
            // TODO: Test searching students by department
            // - Add multiple students with different departments
            // - Search by department
            // - Verify correct students are returned
        }

        @Test
        @DisplayName("Should update student successfully")
        void shouldUpdateStudentSuccessfully() {
            // TODO: Test updating a student
            // - Add a student
            // - Update student details
            // - Verify student is updated
        }

        @Test
        @DisplayName("Should return false when updating non-existent student")
        void shouldReturnFalseWhenUpdatingNonExistentStudent() {
            // TODO: Test updating non-existent student
            // - Attempt to update non-existent student
            // - Verify false is returned
        }

        @Test
        @DisplayName("Should delete student successfully")
        void shouldDeleteStudentSuccessfully() {
            // TODO: Test deleting a student
            // - Add a student
            // - Delete student
            // - Verify student is deleted
        }

        @Test
        @DisplayName("Should return false when deleting non-existent student")
        void shouldReturnFalseWhenDeletingNonExistentStudent() {
            // TODO: Test deleting non-existent student
            // - Attempt to delete non-existent student
            // - Verify false is returned
        }
    }

    @Nested
    @DisplayName("Borrow Record Operations Tests")
    class BorrowRecordOperationsTests {

        @Test
        @DisplayName("Should add borrow record successfully")
        void shouldAddBorrowRecordSuccessfully() {
            // TODO: Test adding a borrow record
            // - Add borrow record to database
            // - Verify record is added
            // - Verify record can be retrieved
        }

        @Test
        @DisplayName("Should throw exception when adding null borrow record")
        void shouldThrowExceptionWhenAddingNullBorrowRecord() {
            // TODO: Test adding null borrow record
            // - Attempt to add null borrow record
            // - Verify appropriate exception is thrown
        }

        @Test
        @DisplayName("Should throw exception when adding duplicate borrow record")
        void shouldThrowExceptionWhenAddingDuplicateBorrowRecord() {
            // TODO: Test adding duplicate borrow record
            // - Add a borrow record
            // - Attempt to add another record with same ID
            // - Verify appropriate exception is thrown
        }

        @Test
        @DisplayName("Should retrieve borrow record by ID")
        void shouldRetrieveBorrowRecordById() {
            // TODO: Test retrieving borrow record by ID
            // - Add a borrow record
            // - Retrieve record by ID
            // - Verify correct record is returned
        }

        @Test
        @DisplayName("Should return null for non-existent borrow record")
        void shouldReturnNullForNonExistentBorrowRecord() {
            // TODO: Test retrieving non-existent borrow record
            // - Attempt to retrieve record with non-existent ID
            // - Verify null is returned
        }

        @Test
        @DisplayName("Should get all borrow records")
        void shouldGetAllBorrowRecords() {
            // TODO: Test getting all borrow records
            // - Add multiple borrow records
            // - Get all records
            // - Verify all records are returned
        }

        @Test
        @DisplayName("Should search borrow records by student")
        void shouldSearchBorrowRecordsByStudent() {
            // TODO: Test searching borrow records by student
            // - Add multiple borrow records for different students
            // - Search by student ID
            // - Verify correct records are returned
        }

        @Test
        @DisplayName("Should search borrow records by book")
        void shouldSearchBorrowRecordsByBook() {
            // TODO: Test searching borrow records by book
            // - Add multiple borrow records for different books
            // - Search by book ISBN
            // - Verify correct records are returned
        }

        @Test
        @DisplayName("Should get overdue records")
        void shouldGetOverdueRecords() {
            // TODO: Test getting overdue records
            // - Add multiple borrow records with different due dates
            // - Get overdue records
            // - Verify only overdue records are returned
        }

        @Test
        @DisplayName("Should update borrow record successfully")
        void shouldUpdateBorrowRecordSuccessfully() {
            // TODO: Test updating a borrow record
            // - Add a borrow record
            // - Update record details
            // - Verify record is updated
        }

        @Test
        @DisplayName("Should return false when updating non-existent borrow record")
        void shouldReturnFalseWhenUpdatingNonExistentBorrowRecord() {
            // TODO: Test updating non-existent borrow record
            // - Attempt to update non-existent record
            // - Verify false is returned
        }
    }

    @Nested
    @DisplayName("Utility Methods Tests")
    class UtilityMethodsTests {

        @Test
        @DisplayName("Should clear all data")
        void shouldClearAllData() {
            // TODO: Test clearing all data
            // - Add books, students, and borrow records
            // - Clear all data
            // - Verify all collections are empty
        }

        @Test
        @DisplayName("Should get total books count")
        void shouldGetTotalBooksCount() {
            // TODO: Test getting total books count
            // - Add multiple books
            // - Get total count
            // - Verify correct count is returned
        }

        @Test
        @DisplayName("Should get total students count")
        void shouldGetTotalStudentsCount() {
            // TODO: Test getting total students count
            // - Add multiple students
            // - Get total count
            // - Verify correct count is returned
        }

        @Test
        @DisplayName("Should get total borrow records count")
        void shouldGetTotalBorrowRecordsCount() {
            // TODO: Test getting total borrow records count
            // - Add multiple borrow records
            // - Get total count
            // - Verify correct count is returned
        }
    }

    @Nested
    @DisplayName("Thread Safety Tests")
    class ThreadSafetyTests {

        @Test
        @DisplayName("Should handle concurrent book additions")
        void shouldHandleConcurrentBookAdditions() throws InterruptedException {
            // TODO: Test concurrent book additions
            // - Create multiple threads adding books
            // - Verify all books are added correctly
            // - Verify no data corruption occurs
        }

        @Test
        @DisplayName("Should handle concurrent student additions")
        void shouldHandleConcurrentStudentAdditions() throws InterruptedException {
            // TODO: Test concurrent student additions
            // - Create multiple threads adding students
            // - Verify all students are added correctly
            // - Verify no data corruption occurs
        }

        @Test
        @DisplayName("Should handle concurrent read and write operations")
        void shouldHandleConcurrentReadAndWriteOperations() throws InterruptedException {
            // TODO: Test concurrent read and write operations
            // - Create threads performing reads and writes simultaneously
            // - Verify all operations complete successfully
            // - Verify data consistency is maintained
        }

        @Test
        @DisplayName("Should handle concurrent searches")
        void shouldHandleConcurrentSearches() throws InterruptedException {
            // TODO: Test concurrent searches
            // - Create multiple threads performing searches
            // - Verify all searches complete successfully
            // - Verify correct results are returned
        }

    }

}
