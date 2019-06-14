package ua.nure.library.web.controller.command;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.Messages;
import ua.nure.library.util.Path;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class DeleteReaderCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    String returnPage = Path.LIST_READERS;
    try {
      Long readerId = Long.parseLong(request.getParameter("id"));
      Reader readerFromDb = readerRepository.getById(readerId);
      if (readerFromDb == null) {
        response.sendRedirect(Path.LIST_READERS);
      }
      readerRepository.delete(readerFromDb);
    } catch (NumberFormatException e) {
      log.error(Messages.ERROR_DELETE_UNABLE_PARSE, e);
    } catch (IOException e) {
      log.error(Messages.ERROR_UNABLE_REDIRECT_READERS, e);
    } catch (DaoException e) {
      log.error(Messages.ERROR_UNABLE_REDIRECT_READERS);
      returnPage = ErrorRedirect.ERROR_MAIN_MENU_URL + Messages.ERROR_UNABLE_REDIRECT_READERS;
    }
    return returnPage;
  }
}
