package ua.nure.library.web.controller.command;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.user.dao.reader.ReaderRepository;
import ua.nure.library.model.user.entity.Reader;
import ua.nure.library.util.Messages;
import ua.nure.library.util.PagesPath;
import ua.nure.library.util.repository.RepositoryFactoryProducer;
import ua.nure.library.web.controller.redirect.ErrorRedirect;

/**
 * @author Artem Kudria
 */
@Log4j
public class ReadersCommand implements Command {

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    ReaderRepository readerRepository = RepositoryFactoryProducer.repositoryFactory()
        .getReaderRepository();

    String returnPage = PagesPath.LIST_READERS;
    try {
      List<Reader> readers = readerRepository.getAll()
          .stream()
          .sorted(Comparator.comparing(Reader::getId))
          .collect(Collectors.toList());
      request.setAttribute("readers", readers);
    } catch (DaoException e) {
      log.error(Messages.ERROR_GET_ALL_READERS, e);
      returnPage = ErrorRedirect.ERROR_READERS_URL + Messages.ERROR_GET_ALL_READERS;
    }
    return returnPage;
  }
}
