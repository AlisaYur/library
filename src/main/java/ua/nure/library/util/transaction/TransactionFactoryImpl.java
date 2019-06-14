package ua.nure.library.util.transaction;

import java.sql.Connection;
import java.util.concurrent.Callable;
import lombok.extern.log4j.Log4j;
import ua.nure.library.util.connection.DBWorker;

/**
 * @author Artem Kudria
 */
@Log4j
public class TransactionFactoryImpl implements TransactionFactory {

  @Override
  public <T> T inTransaction(Callable<T> unitOfWork) throws Exception {
    Connection connection = DBWorker.getDbWorker().getConnection();
    try {
      connection.setAutoCommit(false);
      connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
      T result = unitOfWork.call();
      connection.commit();
      return result;
    } catch (Exception e) {
      connection.rollback();
      throw e;
    } finally {
      connection.close();
    }
  }

  @Override
  public <T> T withoutTransaction(Callable<T> unitOfWork) throws Exception {
    return unitOfWork.call();
  }
}
