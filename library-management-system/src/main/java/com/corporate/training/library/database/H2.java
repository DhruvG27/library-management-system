package com.corporate.training.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class H2 {
  private static final String URL = "jdbc:h2:mem:libdb;DB_CLOSE_DELAY=-1;MODE=MySQL";
  private static final String User = "sa";
  private static final String Password = "";



//  private static final String URL = System.getProperty(
//      "h2.url",
//      DEFAULT_URL
//  );

  private H2() {

  }
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, User, Password);

  }

}
