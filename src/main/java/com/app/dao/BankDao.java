package com.app.dao;

import com.app.model.Bank;
import com.app.services.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BankDao {
    private EntityManager entityManager = EntityManagerService.getEntityManager();
    private EntityTransaction transaction = entityManager.getTransaction();

    public boolean saveBank(final Bank bank) {
        try {
            transaction.begin();
            entityManager.persist(bank);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
        return true;
    }

    public void updateBank(final Bank bank, final Long id) {
        try {
            transaction.begin();
            final Bank bank1 = entityManager.find(Bank.class, id);
            bank1.setAddress(bank.getAddress());
            bank1.setPhone(bank.getPhone());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void deleteBank(final Long bankId) {
        try {
            transaction.begin();
            final Bank bank = entityManager.find(Bank.class, bankId);
            entityManager.remove(bank);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Bank getBank(final Long bankId) {
        transaction.begin();
        final Bank bank = entityManager.find(Bank.class, bankId);
        transaction.commit();
        return bank;
    }
}
