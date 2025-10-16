# Library Management System

## Project Overview

This is a Core Java project designed to test student skills in **Multithreading**, **Collection Framework**, and **Exception Handling**. The project implements a Library Management System with an in-memory database that supports concurrent operations.

## Learning Objectives

Students will practice and demonstrate proficiency in:

1. **Multithreading & Concurrency**
   - Thread-safe collections and operations
   - Synchronization mechanisms (locks, synchronized blocks)
   - CompletableFuture for asynchronous operations
   - Thread pool management
   - Race condition prevention

2. **Collection Framework**
   - Choosing appropriate collections for different use cases
   - Thread-safe collection implementations
   - Collection operations and algorithms
   - Custom data structures and their usage

3. **Exception Handling**
   - Custom exception classes
   - Proper exception propagation
   - Exception hierarchy design
   - Error handling strategies

## Use Case: Library Management System

### Business Requirements

The Library Management System manages books, students, and borrowing operations in a university library. The system must handle concurrent access from multiple users and maintain data consistency.

### Core Entities

1. **Book**
   - ISBN (unique identifier)
   - Title, Author, Category
   - Total copies and available copies
   - Publication date and active status

2. **Student**
   - Student ID (unique identifier)
   - Personal information (name, email, phone, DOB)
   - Department and borrowing limits
   - Current books borrowed and active status

3. **BorrowRecord**
   - Record ID (unique identifier)
   - Student ID and Book ISBN references
   - Borrow date, due date, and return date
   - Status (BORROWED, RETURNED, OVERDUE)
   - Fine amount

### Functional Requirements

#### Book Management
- Add new books to the library
- Search books by title, author, or category
- Update book information
- Remove books (only if no active borrows)
- Track available copies

#### Student Management
- Register new students
- Search students by name or department
- Update student information
- Deactivate students (only if no active borrows)
- Track borrowing limits

#### Borrowing Operations
- Borrow books (with validation)
- Return books
- Track borrowing history
- Calculate fines for overdue books
- Handle concurrent borrowing requests

#### Reporting & Statistics
- Generate library statistics
- Track book utilization rates
- Monitor overdue books
- Calculate outstanding fines

### Technical Requirements

#### Multithreading Requirements
1. **Thread Safety**
   - All database operations must be thread-safe
   - Use appropriate synchronization mechanisms
   - Prevent race conditions in borrowing/returning operations

2. **Concurrent Operations**
   - Support multiple simultaneous book searches
   - Handle concurrent borrowing requests
   - Implement asynchronous operations using CompletableFuture

3. **Performance**
   - Use thread pools for managing concurrent operations
   - Implement efficient locking strategies
   - Minimize contention in high-traffic scenarios

#### Collection Framework Requirements
1. **Data Storage**
   - Choose appropriate collections for different entities
   - Implement efficient search and retrieval operations
   - Use thread-safe collection implementations

2. **Operations**
   - Implement efficient search algorithms
   - Handle large datasets efficiently
   - Use appropriate collection methods for different operations

#### Exception Handling Requirements
1. **Custom Exceptions**
   - Design proper exception hierarchy
   - Create meaningful error messages
   - Include error codes for programmatic handling

2. **Validation**
   - Validate all input parameters
   - Handle business rule violations
   - Provide clear error messages to users

## Project Structure

```
src/
├── main/java/com/corporate/training/library/
│   ├── model/
│   │   ├── Book.java
│   │   ├── Student.java
│   │   ├── BorrowRecord.java
│   │   └── BorrowStatus.java
│   ├── database/
│   │   └── InMemoryDatabase.java
│   ├── service/
│   │   ├── LibraryService.java
│   │   └── LibraryStatistics.java
│   └── exception/
│       ├── LibraryException.java
│       ├── BookNotFoundException.java
│       ├── StudentNotFoundException.java
│       ├── BookNotAvailableException.java
│       ├── StudentLimitExceededException.java
│       ├── InvalidOperationException.java
│       └── ValidationException.java
└── test/java/com/corporate/training/library/
    ├── model/
    │   └── BookTest.java
    ├── service/
    │   └── LibraryServiceTest.java
    └── database/
        └── InMemoryDatabaseTest.java
```

## Implementation Guidelines

### For Students

1. **Start with Model Classes**
   - Implement constructors with proper validation
   - Add getters and setters with validation
   - Implement equals, hashCode, and toString methods
   - Add business logic methods

2. **Implement Database Layer**
   - Choose appropriate thread-safe collections
   - Implement proper locking mechanisms
   - Ensure all operations are thread-safe
   - Handle concurrent access scenarios

3. **Build Service Layer**
   - Implement business logic
   - Add proper exception handling
   - Use CompletableFuture for async operations
   - Ensure thread safety in critical operations

4. **Create Exception Classes**
   - Design meaningful exception hierarchy
   - Add appropriate constructors
   - Include error codes and context information

5. **Write Comprehensive Tests**
   - Test all functionality
   - Include concurrency tests
   - Test exception scenarios
   - Verify thread safety

### Key Implementation Points

#### Thread Safety
- Use `ConcurrentHashMap` for main data storage
- Implement `ReadWriteLock` for better performance
- Synchronize critical sections in borrowing/returning operations
- Use thread-safe collections throughout

#### Exception Handling
- Validate all inputs at service layer
- Throw appropriate custom exceptions
- Include meaningful error messages
- Handle exceptions gracefully

#### Collection Usage
- Use `ConcurrentHashMap` for O(1) lookups
- Use `Collections.synchronizedList()` for thread-safe lists
- Implement efficient search algorithms
- Consider using `Stream` API for complex operations

## Testing Strategy

### Unit Tests
- Test all model classes
- Test database operations
- Test service layer methods
- Test exception scenarios

### Integration Tests
- Test complete workflows
- Test concurrent operations
- Test error handling paths

### Concurrency Tests
- Test thread safety
- Test race conditions
- Test performance under load
- Test deadlock prevention

## Running the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build and Test
```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Run specific test class
mvn test -Dtest=BookTest

# Generate test report
mvn surefire-report:report
```

### Example Usage
```java
// Initialize the library service
LibraryService libraryService = new LibraryService();

// Add a book
Book book = new Book("978-0-123456-78-9", "Java Programming", "John Doe", "Programming", 5);
libraryService.addBook(book);

// Add a student
Student student = new Student("STU001", "Alice", "Smith", "alice@university.edu", "Computer Science");
libraryService.addStudent(student);

// Borrow a book
BorrowRecord record = libraryService.borrowBook("STU001", "978-0-123456-78-9");

// Return the book
libraryService.returnBook(record.getRecordId());

// Get statistics
LibraryStatistics stats = libraryService.getLibraryStatistics();
```

## Evaluation Criteria

### Multithreading (40%)
- Thread safety implementation
- Proper use of synchronization mechanisms
- Concurrent operation handling
- Performance under concurrent load

### Collection Framework (30%)
- Appropriate collection choices
- Efficient algorithms and operations
- Thread-safe collection usage
- Memory and performance optimization

### Exception Handling (20%)
- Custom exception design
- Proper exception propagation
- Meaningful error messages
- Comprehensive error handling

### Code Quality (10%)
- Clean code principles
- Proper documentation
- Test coverage
- Code organization

## Common Pitfalls to Avoid

1. **Thread Safety Issues**
   - Not using thread-safe collections
   - Inadequate synchronization
   - Race conditions in critical sections
   - Deadlock scenarios

2. **Collection Misuse**
   - Using inappropriate collections
   - Inefficient search algorithms
   - Not considering thread safety
   - Memory leaks in collections

3. **Exception Handling Problems**
   - Generic exception handling
   - Not validating inputs
   - Poor error messages
   - Exception swallowing

4. **Performance Issues**
   - Inefficient algorithms
   - Excessive synchronization
   - Memory leaks
   - Poor resource management

## Additional Challenges

### Advanced Multithreading
- Implement custom thread pools
- Use advanced synchronization primitives
- Implement lock-free algorithms
- Handle thread interruption properly

### Advanced Collections
- Implement custom collections
- Use advanced Stream operations
- Optimize for specific use cases
- Implement caching mechanisms

### Advanced Exception Handling
- Implement exception chaining
- Use try-with-resources
- Implement custom error recovery
- Add logging and monitoring

## Support and Resources

- Java Concurrency in Practice (Book)
- Oracle Java Documentation
- JUnit 5 User Guide
- Maven Documentation

## Conclusion

This project provides a comprehensive platform for testing and improving skills in multithreading, collection framework, and exception handling. Students should focus on understanding the business requirements and implementing robust, thread-safe solutions that handle real-world scenarios effectively.

Remember: The goal is not just to make the code work, but to make it work correctly, efficiently, and safely under concurrent access patterns.
