package ua.nure.library.util.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import lombok.extern.log4j.Log4j;

/**
 * @author Artem Kudria
 */
@Log4j
public class TestDbWorker {

  private static TestDbWorker h2DbWorker = null;

  public static synchronized TestDbWorker getH2DbWorker() {
    if (null == h2DbWorker) {
      return new TestDbWorker();
    } else {
      return h2DbWorker;
    }
  }

  public Connection getConnection() {
    String databaseProperties = "db.properties";
    String databaseUrl = "test-database.url";
    String databaseUser = "database.username";
    String databasePass = "database.password";
    Properties properties = new Properties();
    String dbUrl;
    String dbPass;
    String dbUser;
    Connection con = null;
    try {
      InputStream input = Thread.currentThread().getContextClassLoader()
          .getResourceAsStream(databaseProperties);
      properties.load(input);

      dbUrl = properties.getProperty(databaseUrl);
      dbPass = properties.getProperty(databasePass);
      dbUser = properties.getProperty(databaseUser);

      Class.forName("org.postgresql.Driver");
      con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
    } catch (ClassNotFoundException e) {
      log.error("Error get postgresql driver in db! {}", e);
    } catch (SQLException e) {
      log.error("Cannot obtain a connection from the postgresql pool", e);
    } catch (IOException e) {
      log.error("Cannot obtain a properties from file for postgresql", e);
    }
    return con;
  }
}
