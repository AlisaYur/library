package ua.nure.library.util.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j;

/**
 * @author Artem Kudria
 */
@Log4j
public class DBWorker {

  private static DBWorker dbWorker = null;

  public static synchronized DBWorker getDbWorker() {
    if (null == dbWorker) {
      return new DBWorker();
    } else {
      return dbWorker;
    }
  }

  public Connection getConnection() {
    Connection con = null;
    try {
      Class.forName("org.postgresql.Driver");
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup("java:/comp/env");

      DataSource ds = (DataSource) envContext.lookup("jdbc/library");
      con = ds.getConnection();
    } catch (NamingException ex) {
      log.error("Cannot get properties", ex);
    } catch (ClassNotFoundException e) {
      log.info("Error get driver in db! {}", e);
    } catch (SQLException e) {
      log.error("Cannot obtain a connection from the pool", e);
    }
    return con;
  }

  /**
   * Commits and close the given connection.
   *
   * @param con Connection to be committed and closed.
   */
  public void commitAndClose(Connection con) {
    try {
      con.commit();
      con.close();
    } catch (SQLException ex) {
      log.error("Error commit and close!");
    }
  }

  /**
   * Rollbacks and close the given connection.
   *
   * @param con Connection to be rollbacked and closed.
   */
  public void rollbackAndClose(Connection con) {
    try {
      con.rollback();
      con.close();
    } catch (SQLException ex) {
      log.error("Error rollback!");
    }
  }
}
