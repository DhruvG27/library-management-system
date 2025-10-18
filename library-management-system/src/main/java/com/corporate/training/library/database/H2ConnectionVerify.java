package com.corporate.training.library.database;

import java.sql.*;

public class H2ConnectionVerify {
  public static void main(String[] args) throws SQLException {
    System.out.println("--->Checking H2 In-Memory Database Connection....");
    try (Connection conn = H2.getConnection();
         Statement stmt = conn.createStatement()) {
      System.out.println("Connection Successful!");
//      if (conn != null && !conn.isClosed()) {
//        System.out.println("Connection Successful!");
//      } else {
//        System.out.println("Connection Failed!");
//      }
      stmt.execute("DROP TABLE IF EXISTS STUDENTS");
      stmt.execute(("""
          CREATE TABLE STUDENTS(
            ID INT PRIMARY KEY,
            NAME VARCHAR(255)
          )
        """));
      System.out.println("Table Created Successfully!");
//      try (Statement stmt = conn.createStatement() ) {
//        stmt.execute("""
//            CREATE TABLE IF NOT EXISTS STUDENTS(
//            ID INT PRIMARY KEY,\s
//            NAME VARCHAR(255)
//            )
//            """);
//        System.out.println("Table Created Successfully!");


      int rows = stmt.executeUpdate("INSERT INTO STUDENTS (ID, NAME) VALUES (1, 'Dhruv')");
      if (rows > 0) {
        System.out.println("Insert Successful!");
      } else {
        System.out.println("Insert Failed!");
      }

      try (ResultSet rs = stmt.executeQuery("SELECT * FROM STUDENTS")) {
        System.out.println("--->Retrieving Data from STUDENTS Table:");
        while (rs.next()) {
          int id = rs.getInt("ID");
          String name = rs.getString("NAME");
          System.out.println("ID: " + id + ", Name: " + name);
        }
      }
    } catch (SQLIntegrityConstraintViolationException etc) {
      System.out.println("Duplicate Entry! Insert Failed.");
      etc.printStackTrace();

    } catch (SQLException e) {
      System.out.println("Connection Failed!");
      e.printStackTrace();
    }
    System.out.println("Database Execution Completed Successfully!");
  }

}
