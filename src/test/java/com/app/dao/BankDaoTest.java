package com.app.dao;

import com.app.entities.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankDaoTest {

    @Test
    void saveBankTest() {
        final Bank bank = new Bank();
        bank.setName("Raiffeisen Bank");
        bank.setId(8766);

        final BankDao bankDao = new BankDao();
        bankDao.saveBank(bank);

        final Bank resultBank = bankDao.getBank(bank.getId());

        assertEquals(bank, resultBank);
    }
}
