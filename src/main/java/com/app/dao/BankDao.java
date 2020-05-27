package com.app.dao;

import com.app.model.Bank;
import com.app.services.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BankDao {
    private EntityManager entityManager = EntityManagerService.getEntityManager();
    private EntityTransaction transaction = entityManager.getTransaction();

    public boolean saveBank(Bank bank) {
        try {
            transaction.begin();
            entityManager.persist(bank);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return true;
    }

    public void updateBank(int bankId, String bankName) {
        try {
            transaction.begin();
            final Bank bank = entityManager.find(Bank.class, bankId);
            bank.setBankName(bankName);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void deleteBank(int bankId) {
        try {
            transaction.begin();
            final Bank bank = entityManager.find(Bank.class, bankId);
            entityManager.remove(bank);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Bank getBank(int bankId) {
        transaction.begin();
        final Bank bank = entityManager.find(Bank.class, bankId);
        transaction.commit();
        return bank;
    }
}
