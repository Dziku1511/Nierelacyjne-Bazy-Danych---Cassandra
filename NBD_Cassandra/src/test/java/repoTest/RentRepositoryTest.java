package repoTest;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import java.time.LocalDate;
import java.util.UUID;
import model.Rent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.RentRepository;

class RentRepositoryTest {

    private static RentRepository rentRepository;
    private static UUID rentId;
    private static UUID clientId;
    private static UUID bookId;

    @BeforeEach
    public void setUp() {
        rentRepository = new RentRepository();
        bookId = UUID.randomUUID();
        rentId = UUID.randomUUID();
        clientId = UUID.randomUUID();

    }

    @AfterAll
    public static void inTheEnd() {
        try {
            rentRepository.close();
        }    catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addRent() {
        Rent rent = new Rent(rentId,LocalDate.now(),LocalDate.now().plusDays(25),clientId,bookId);
        rentRepository.addRent(rent, "Janusz", "Jasny plomien ovirtu");
        ResultSet resultSet = rentRepository.getRentsByClient(clientId);
        for (var row : resultSet) {
            System.out.println(
                    row.getUuid("rentId") + " " + row.getUuid("bookId") + " " + row.getUuid("clientId") + " " + row.getString("name") +
                            " " + row.getLocalDate("begin") + " " + row.getLocalDate("end"));
        }

        ResultSet resultSet1 = rentRepository.getRentsByBook(bookId);
        for (var row : resultSet1) {
            System.out.println(
                    row.getUuid("rentId") + " " + row.getUuid("bookId") + " " + row.getUuid("clientId") + " " + row.getString("title") +
                            " " + row.getLocalDate("begin") + " " + row.getLocalDate("end"));
        }

    }
}
