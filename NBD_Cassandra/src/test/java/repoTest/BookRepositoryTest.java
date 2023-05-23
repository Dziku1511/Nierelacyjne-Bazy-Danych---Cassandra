package repoTest;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import java.util.UUID;
import model.Book;
import model.Client;
import org.junit.jupiter.api.*;
import repository.BookRepository;


class BookRepositoryTest {

    private static BookRepository bookRepository;
    private static UUID bookId;

    @BeforeEach
    public void setUp() {
        bookRepository = new BookRepository();
        bookId = UUID.randomUUID();
    }

    @AfterAll
    public static void inTheEnd() {
        try {
            bookRepository.close();
        }    catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(1)
    public void testAddAndGetBook() {
        Book book = new Book(bookId, "Do Lodexu i z powrotem","Horror","Imperator M.", 666);
        bookRepository.addBook(book);

        ResultSet resultSet = bookRepository.getBook(bookId);
        for (var row : resultSet) {
            System.out.println(row.getUuid("bookId") + " " + row.getString("title") + " " +
                    row.getString("author") + " " + row.getString("genre") + " " +
                    row.getInt("pageNumber"));
        }
    }

    @Test
    @Order(2)
    public void testUpdateBook() {
        Book updatedBook = new Book(bookId, "Jasny plomien ovirtu","Religijna","Uzurpator S.", 2317);
        bookRepository.update(updatedBook);

        ResultSet resultSet = bookRepository.getBook(bookId);
        for (var row : resultSet) {
            System.out.println(row.getUuid("bookId") + " " + row.getString("title") + " " +
                    row.getString("author") + " " + row.getString("genre") + " " +
                    row.getInt("pageNumber"));
        }
    }

    @Test
    @Order(3)
    public void testDeleteBook() {
        bookRepository.removeBook(bookId);
        ResultSet resultSet = bookRepository.getBook(bookId);
        for (var row : resultSet) {
            System.out.println(row.getUuid("bookId") + " " + row.getString("title") + " " +
                    row.getString("author") + " " + row.getString("genre") + " " +
                    row.getInt("pageNumber"));
        }
    }



}
