package com.app.dao;

import com.app.model.Client;
import com.app.services.EntityManagerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClientDaoTest {

    private final ClientDao clientDao = new ClientDao();

    private static Client firstClient;

    private static Client secondClient;

    private static Client thirdClient;

    @BeforeAll
    static void setUp() {
        EntityManager entityManager = EntityManagerService.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.createQuery("delete from Client").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 1").executeUpdate();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }

        firstClient = new Client();
        firstClient.setFirstName("John");
        firstClient.setLastName("Doe");
        firstClient.setPhone("890900087");
        firstClient.setAddress("ABC street, 78, Amsterdam, Netherlands");

        secondClient = new Client();
        secondClient.setFirstName("Jane");
        secondClient.setLastName("Doe");
        secondClient.setPhone("89090008766");
        secondClient.setAddress("ABC street, 58, Paris, France");

        thirdClient = new Client();
        thirdClient.setFirstName("Jim");
        thirdClient.setLastName("Doe");
        thirdClient.setPhone("867090008766");
        thirdClient.setAddress("ABCE street, 23, Rotterdam, Netherlands");

        try {
            transaction.begin();
            entityManager.persist(firstClient);
            entityManager.persist(secondClient);
            entityManager.persist(thirdClient);
            transaction.commit();
        }catch (final Exception e) {
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }


    @Test
    void saveClientTest() {
        final Client saveClient = new Client();
        saveClient.setFirstName("Maurice");
        saveClient.setLastName("Placard");
        saveClient.setPhone("890907087");
        saveClient.setAddress("ABCD street, 78, Skopje");

        clientDao.saveClient(saveClient);
        final Client actual = clientDao.getClient(saveClient.getId());

        assertEquals(saveClient.getFirstName(), actual.getFirstName());
        assertEquals(saveClient.getLastName(), actual.getLastName());
        assertEquals(saveClient.getPhone(), actual.getPhone());
        assertEquals(saveClient.getAddress(), actual.getAddress());
    }

    @Test
    void getClientTest() {
        final Client resultClient = clientDao.getClient(firstClient.getId());
        assertEquals(firstClient.getFirstName(), resultClient.getFirstName());
        assertEquals(firstClient.getPhone(), resultClient.getPhone());
    }

    @Test
    void deleteClientTest() {
        clientDao.deleteClient(secondClient.getId());
        final Client resultClient = clientDao.getClient(secondClient.getId());

        assertNull(resultClient);
    }

    @Test
    void updateClientTest() {
        final Client updateClient = new Client();
        updateClient.setPhone("890907087");
        updateClient.setAddress("ABCD street, 78, Skopje");
        updateClient.setDateOfBirth(OffsetDateTime.now());

        clientDao.updateClient(updateClient, thirdClient.getId());
        final Client resultClient = clientDao.getClient(thirdClient.getId());

        assertEquals(thirdClient.getFirstName(), resultClient.getFirstName());
        assertEquals(thirdClient.getAddress(), resultClient.getAddress());
        assertEquals(thirdClient.getPhone(), resultClient.getPhone());
        assertEquals(thirdClient.getDateOfBirth(), resultClient.getDateOfBirth());
    }

    @Test
    void saveClientColumnDefinitionTest() {
        final Client saveClient = new Client();
        saveClient.setFirstName("Maurice-Jean-Baptiste-Alphonse");
        saveClient.setLastName("Placard");
        saveClient.setPhone("890907087");
        saveClient.setAddress("ABCD street, 78, Skopje");

        clientDao.saveClient(saveClient);
        final Client actual = clientDao.getClient(saveClient.getId());

        assertNull(actual);
    }

    @Test
    void saveClientLengthAttributeTest() {
        final Client saveClient = new Client();
        saveClient.setFirstName("Maurice");
        saveClient.setLastName("Placard-Jean-Baptiste-Alphonse");
        saveClient.setPhone("890907087");
        saveClient.setAddress("ABCD street, 78, Skopje");

        clientDao.saveClient(saveClient);
        final Client actual = clientDao.getClient(saveClient.getId());

        assertNull(actual);
    }

    @Test
    void columnPrecisionAttributeTest() {
        final Client expected = new Client();
        expected.setFirstName("Maurice");
        expected.setLastName("Jean-Baptiste");
        expected.setPhone("890907087");
        expected.setAddress("ABCD street, 78, Skopje");
        expected.setAmount(BigDecimal.valueOf(1000.9920)); //precision=8, scale=4

        clientDao.saveClient(expected);
        final Client actual = clientDao.getClient(expected.getId());

        assertNull(actual);
    }
}
