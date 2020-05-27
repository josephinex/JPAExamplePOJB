package com.app.dao;

import com.app.model.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BankDaoTest {

    private final BankDao bankDao = new BankDao();

    @Test
    void saveBankTest() {
        final Bank bank = new Bank();
        bank.setBankName("Raiffeisen Bank");
        bankDao.saveBank(bank);
        final Bank resultBank = bankDao.getBank(bank.getId());

        assertEquals(bank, resultBank);
    }

    @Test
    void deleteBankTest() {
        final Bank bank = new Bank();
        bank.setBankName("Rai Bank");
        bankDao.saveBank(bank);

        bankDao.deleteBank(bank.getId());
        final Bank resultBank = bankDao.getBank(bank.getId());

        assertNull(resultBank);
    }

    @Test
    void updateBankTest() {
        final Bank bank = new Bank();
        bank.setBankName("Raif Bank");
        bankDao.saveBank(bank);

        bankDao.updateBank(bank.getId(), "MAIB");
        final Bank resultBank = bankDao.getBank(bank.getId());

        assertEquals(resultBank.getBankName(), "MAIB");
    }
}
