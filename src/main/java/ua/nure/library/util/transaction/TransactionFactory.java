package ua.nure.library.util.transaction;

import java.util.concurrent.Callable;

/**
 * @author Artem Kudria
 */
public interface TransactionFactory {

  <T> T inTransaction(Callable<T> unitOfWork) throws Exception;

  <T> T withoutTransaction(Callable<T> unitOfWork) throws Exception;
}
