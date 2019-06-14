package ua.nure.library.model.book.dao.author;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class AuthorServiceTest {

  private AuthorRepository authorRepository = RepositoryFactoryProducer.repositoryFactory()
      .getAuthorTestRepository();

  @Test
  public void create() throws DaoException {
    Author author = new Author("Artem", "Kudria");
    authorRepository.create(author);
    final Author authorFromDb = authorRepository.getByLastName(author.getLastName());
    assertEquals("Kudria", authorFromDb.getLastName());
  }

  @Test
  public void update() throws DaoException {
    Author author = new Author("Sergey", "Brin");
    authorRepository.create(author);
    Author authorToUpdate = authorRepository.getByLastName(author.getLastName());
    authorToUpdate.setFirstName("Anthony");
    authorRepository.update(authorToUpdate.getId(), authorToUpdate);
    final Author authorWithNewValues = authorRepository.getById(authorToUpdate.getId());
    assertEquals("Anthony", authorWithNewValues.getFirstName());
  }

  @Test
  public void delete() throws DaoException {
    Author author = new Author("Steve", "Mops");
    authorRepository.create(author);
    final Author authorFromDbToDelete = authorRepository.getByLastName(author.getLastName());
    authorRepository.delete(authorFromDbToDelete);
    Author authorFromDb = authorRepository.getByLastName(author.getLastName());
    assertNull(authorFromDb.getLastName());
  }

  @Test
  public void getById() throws DaoException {
    Author author = new Author(20L, "Joseph", "Falco", null);
    authorRepository.create(author);
    Author authorFromDb = authorRepository.getById(author.getId());
    assertNotNull(authorFromDb);
  }

  @Test
  public void getByLastName() throws DaoException {
    Author author = new Author("Lionel", "Messi");
    authorRepository.create(author);
    Author authorFromDb = authorRepository.getByLastName(author.getLastName());
    assertEquals("Messi", authorFromDb.getLastName());
  }

  @Test
  public void getAll() throws DaoException {
    List<Author> authors = authorRepository.getAll();
    assertFalse(authors.isEmpty());
  }

  @Test
  public void searchAuthorsByFirstOrLastName() throws DaoException {
    Author author = new Author("Stas", "Mikhajlov");
    authorRepository.create(author);
    List<Author> authors = authorRepository.searchAuthorsByFirstOrLastName("Mikhaj");
    assertFalse(authors.isEmpty());
  }
}