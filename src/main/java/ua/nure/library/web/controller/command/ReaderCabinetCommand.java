package ua.nure.library.web.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.order.entity.Order;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.User;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class ReaderCabinetCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();
    String returnPage = PagesPath.READER_CAB;
    try {
      User user = (User) request.getSession(false).getAttribute("user");
      List<Order> orders = readerRepository.getAllOrdersReader(user.getLogin());
      request.setAttribute("orders", orders);

    } catch (DaoException e) {
      log.error(Messages.ERROR_GET_READER_CABINET);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_GET_READER_CABINET;
    }
    return returnPage;
  }
}
