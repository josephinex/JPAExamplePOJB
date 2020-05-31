package com.app.dao;

import com.app.model.Bank;
import com.app.services.EntityManagerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

public class BankDaoTest {

    private final BankDao bankDao = new BankDao();

    private static Bank primaryBank;

    private static Bank secondaryBank;

    private static Bank tertiaryBank;

    @BeforeAll
    static void setUp() {
        EntityManager entityManager = EntityManagerService.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.createQuery("delete from Bank").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE HIBERNATE_SEQUENCE RESTART WITH 1").executeUpdate();
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }

        primaryBank = new Bank();
        primaryBank.setBankName("Raiffeisen Bank");
        primaryBank.setPhone("890900087");
        primaryBank.setAddress("ABC street, 78, Amsterdam, Netherlands");

        secondaryBank = new Bank();
        secondaryBank.setBankName("Société Générale");
        secondaryBank.setPhone("89090008766");
        secondaryBank.setAddress("ABC street, 58, Paris, France");

        tertiaryBank = new Bank();
        tertiaryBank.setBankName("ING Group");
        tertiaryBank.setPhone("867090008766");
        tertiaryBank.setAddress("ABCE street, 23, Rotterdam, Netherlands");

        try {
            transaction.begin();
            entityManager.persist(primaryBank);
            entityManager.persist(secondaryBank);
            entityManager.persist(tertiaryBank);
            transaction.commit();
        }catch (final Exception e) {
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

    @Test
    void saveBankTest() {
        final Bank saveBank = new Bank();
        saveBank.setBankName("MAIB");
        saveBank.setPhone("890907087");
        saveBank.setAddress("ABCD street, 78, Skopje");

        bankDao.saveBank(saveBank);
        final Bank actual = bankDao.getBank(saveBank.getId());

        assertEquals(saveBank.getBankName(), actual.getBankName());
        assertEquals(saveBank.getPhone(), actual.getPhone());
    }

    @Test
    void getBankTest() {
        final Bank resultBank = bankDao.getBank(primaryBank.getId());
        assertEquals(primaryBank.getBankName(), resultBank.getBankName());
        assertEquals(primaryBank.getPhone(), resultBank.getPhone());
    }

    @Test
    void deleteBankTest() {
        bankDao.deleteBank(secondaryBank.getId());
        final Bank resultBank = bankDao.getBank(secondaryBank.getId());

        assertNull(resultBank);
    }

    @Test
    void updateBankTest() {
        final Bank updateBank = new Bank();
        updateBank.setPhone("890907087");
        updateBank.setAddress("ABCD street, 78, Skopje");

        bankDao.updateBank(updateBank, tertiaryBank.getId());
        final Bank resultBank = bankDao.getBank(tertiaryBank.getId());

        assertEquals(tertiaryBank.getBankName(), resultBank.getBankName());
        assertNotEquals(tertiaryBank.getAddress(), resultBank.getAddress());
        assertNotEquals(tertiaryBank.getPhone(), resultBank.getPhone());
    }
}
