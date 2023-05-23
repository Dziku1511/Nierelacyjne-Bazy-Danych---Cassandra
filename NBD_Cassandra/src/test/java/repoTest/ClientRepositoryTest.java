package repoTest;


import com.datastax.oss.driver.api.core.cql.ResultSet;
import java.util.UUID;
import model.Client;
import org.junit.jupiter.api.*;
import repository.ClientRepository;

import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientRepositoryTest {

       private static ClientRepository clientRepository;
       private static UUID clientId;

       @BeforeEach
        public void setUp() {
           clientRepository = new ClientRepository();
           clientId = UUID.randomUUID();
       }

       @AfterAll
       public static void inTheEnd() {
           try {
               clientRepository.close();
           }    catch (Exception e) {
               e.printStackTrace();
           }
       }

       @Test
       @Order(1)
       public void testAddAndGetClient() {
           Client client = new Client(clientId, "Janusz", "Moczywas");
           clientRepository.addClient(client);

           ResultSet resultSet = clientRepository.getClient(clientId);
           for (var row : resultSet) {
               System.out.println(
                       row.getUuid("clientId") + " " + row.getString("firstName") + " " + row.getString("lastName"));
           }
       }

       @Test
       @Order(2)
       public void testUpdateClient() {
           Client updatedClient = new Client(clientId, "Grazyna", "Sprezyna");
           clientRepository.update(updatedClient);

           ResultSet resultSet = clientRepository.getClient(clientId);
           for (var row : resultSet) {
               System.out.println(
                       row.getUuid("clientId") + " " + row.getString("firstName") + " " + row.getString("lastName"));
           }
       }

       @Test
       @Order(3)
       public void testDeleteClient() {
           clientRepository.removeClient(clientId);
           ResultSet resultSet = clientRepository.getClient(clientId);
           for (var row : resultSet) {
               System.out.println(
                       row.getUuid("clientId") + " " + row.getString("firstName") + " " + row.getString("lastName"));
           }
       }

}