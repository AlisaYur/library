package ua.nure.library.model.book.dao.book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ua.nure.library.exception.DaoException;
import ua.nure.library.model.book.entity.Author;
import ua.nure.library.model.book.entity.Book;
import ua.nure.library.model.book.entity.Genre;
import ua.nure.library.util.repository.RepositoryFactoryProducer;

@RunWith(JUnit4.class)
public class BookServiceTest {

  private BookRepository bookRepository = RepositoryFactoryProducer.repositoryFactory()
      .getBookTestRepository();
  private Author author;
  private Genre genre;

  @Before
  public void init() {
    String authorLastName = "Brook" + new Random().nextInt();
    String genreName = "Bestseller" + new Random().nextInt();
    author = Author.builder()
        .id(1L)
        .firstName("Joseph")
        .lastName(authorLastName)
        .build();
    genre = Genre.builder()
        .id(1L)
        .name(genreName)
        .build();
  }

  @Test
  public void create() throws DaoException {
    Book book = Book.builder()
        .title("Spring")
        .publishingHouse("SpringHouse")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    Book bookFromDb = bookRepository.getByTitle(book.getTitle());
    assertEquals(book.getTitle(), bookFromDb.getTitle());
  }

  @Test
  public void update() throws DaoException {
    String title = "WrongTitle";
    Book book = Book.builder()
        .title(title)
        .publishingHouse("SpringHouse")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author)
        .image(new byte[]{})
        .encodedImage("").build();
    bookRepository.create(book);
    Book bookToUpdate = bookRepository.getByTitle(title);
    bookToUpdate.setTitle("RightTitle");
    bookRepository.update(bookToUpdate.getId(), bookToUpdate);
    assertEquals(bookToUpdate.getTitle(),
        bookRepository.getByTitle(bookToUpdate.getTitle()).getTitle());
  }

  @Test
  public void delete() throws DaoException {
    Book book = Book.builder()
        .title("Delete")
        .publishingHouse("SpringHouse")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    Book bookFromDbToDelete = bookRepository.getByTitle(book.getTitle());
    bookRepository.delete(bookFromDbToDelete);
    Book bookAfterDelete = bookRepository.getByTitle(bookFromDbToDelete.getTitle());
    assertNull(bookAfterDelete);
  }

  @Test
  public void getById() throws DaoException {
    Book book = Book.builder()
        .title("Java")
        .publishingHouse("JavaForFun")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    Book bookFromDb = bookRepository.getByTitle(book.getTitle());
    Book bookById = bookRepository.getById(bookFromDb.getId());
    assertNotNull(bookById);

  }

  @Test
  public void getByTitle() throws DaoException {
    Book book = Book.builder()
        .title("ProJava")
        .publishingHouse("Bad")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    Book byTitle = bookRepository.getByTitle(book.getTitle());
    assertEquals(book.getTitle(), byTitle.getTitle());
  }

  @Test
  public void getByAuthor() throws DaoException {
    BookRepository bookRepository = mock(BookService.class);
    Book book = Book.builder()
        .title("JavaScript")
        .publishingHouse("Bad")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    when(bookRepository.getByAuthor(anyString()))
        .thenReturn(new ArrayList<>(Collections.singletonList(book)));
    List<Book> authorBooks = bookRepository.getByAuthor(author.getLastName());
    assertFalse(authorBooks.isEmpty());
  }

  @Test
  public void getByGenre() throws DaoException {
    BookRepository bookRepository = mock(BookService.class);
    Book book = Book.builder()
        .title("MySQL")
        .publishingHouse("Base")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    when(bookRepository.getByGenre(anyString())).thenReturn(Collections.singletonList(book));
    List<Book> genreBooks = bookRepository.getByGenre(genre.getName());
    assertFalse(genreBooks.isEmpty());
  }

  @Test
  public void getByPublishingHouse() throws DaoException {
    Book book = Book.builder()
        .title("PublHouse")
        .publishingHouse("Base")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    List<Book> byPublishingHouse = bookRepository.getByPublishingHouse(book.getPublishingHouse());
    assertFalse(byPublishingHouse.isEmpty());
  }

  @Test
  public void getAll() throws SQLException, DaoException {
    List<Book> books = bookRepository.getAll();
    assertFalse(books.isEmpty());
  }

  @Test
  public void sortBooksByType() throws DaoException, SQLException {
    Book book = Book.builder()
        .title("Sorted")
        .publishingHouse("Base")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    List<Book> books = bookRepository.getAll();
    List<Book> sortedBooks = bookRepository.sortBooksByType("title_asc", books);
    assertNotEquals(books, sortedBooks);
  }

  @Test
  public void searchBookByTitleOrAuthor() throws DaoException {
    Book book = Book.builder()
        .title("Search")
        .publishingHouse("Base")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    List<Book> bookFromDb = bookRepository.searchBookByTitleOrAuthor(book.getTitle());
    assertNotNull(bookFromDb);
  }

  @Test
  public void getBooksByFilter() throws DaoException {
    BookRepository bookRepository = mock(BookService.class);
    Book book = Book.builder()
        .title("Filter")
        .publishingHouse("Base")
        .countInStock(10L)
        .dateOfPublication(Date.valueOf(LocalDate.now()))
        .genre(genre)
        .author(author).build();
    bookRepository.create(book);
    when(bookRepository.getBooksByFilter(anyListOf(String.class), anyListOf(String.class)))
        .thenReturn(new ArrayList<>(Collections.singletonList(book)));
    List<Book> booksByFilter = bookRepository
        .getBooksByFilter(new ArrayList<>(), new ArrayList<>());
    assertFalse(booksByFilter.isEmpty());
  }
}