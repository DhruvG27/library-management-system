package com.corporate.training.library.database;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class H2 {
//  private static final String URL =
//    "jdbc:h2:tcp://localhost/mem:library;MODE=PostgreSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1";
//
//  private static final String User = "sa";
//  private static final String Password = "";
//
//  static {
//    try {
//      Class.forName("org.h2.Driver");
//    } catch (ClassNotFoundException e) {
//      throw new ExceptionInInitializerError(e);
//    }
//  }
//
////  private static final String URL = System.getProperty(
////      "h2.url",
////      DEFAULT_URL
////  );
////
////  private H2() {
////
////  }

  private static final String USER = "sa";
  private static final String PASSWORD = "";

  // DB file will be under <project>/h2/library.*
  private static final String URL = "jdbc:h2:file:" +
    Paths.get(System.getProperty("user.dir"), "h2", "library").toString() +
    ";AUTO_SERVER=TRUE;MODE=PostgreSQL;DATABASE_TO_UPPER=false";

  static {
    try { Class.forName("org.h2.Driver"); }
    catch (ClassNotFoundException e) { throw new RuntimeException(e); }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);

  }

}
