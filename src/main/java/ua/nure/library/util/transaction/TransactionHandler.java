package ua.nure.library.util.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;

/**
 * @author Artem Kudria
 */
@Log4j
@AllArgsConstructor
public class TransactionHandler implements InvocationHandler {

  private Object object;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    TransactionFactory transactionFactory = new TransactionFactoryImpl();
    try {
      if (isTransactionAnnotationPresent(method)) {
        return transactionFactory.inTransaction(() -> method.invoke(object, args));
      }

      return transactionFactory.withoutTransaction(() -> method.invoke(object, args));
    } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
      log.error(e.getMessage());
      throw new DaoException(e.getMessage());
    }
  }


  private boolean isTransactionAnnotationPresent(Method method) {
    try {
      Method invokedMethod = object.getClass().getMethod(
          method.getName(), method.getParameterTypes());
      invokedMethod.getDeclaredAnnotations();
      return invokedMethod.isAnnotationPresent(Transactional.class);
    } catch (NoSuchMethodException e) {
      log.debug(
          "Error in the TransactionHandler, no such method inside TransactionAnnotationPresent");
      return false;
    }
  }
}
