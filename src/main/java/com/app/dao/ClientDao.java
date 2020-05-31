package com.app.dao;

import com.app.model.Client;
import com.app.services.EntityManagerService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ClientDao {
    private EntityManager entityManager = EntityManagerService.getEntityManager();
    private EntityTransaction transaction = entityManager.getTransaction();

    public boolean saveClient(final Client client) {
        try {
            transaction.begin();
            entityManager.clear();
            entityManager.persist(client);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
        return true;
    }

    public void updateClient(final Client client, final Long id) {
        try {
            transaction.begin();
            final Client client1 = entityManager.find(Client.class, id);
            client1.setDateOfBirth(client.getDateOfBirth());
            entityManager.clear();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void deleteClient(final Long clientId) {
        try {
            transaction.begin();
            final Client client = entityManager.find(Client.class, clientId);
            entityManager.remove(client);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Client getClient(final Long clientId) {
        transaction.begin();
        final Client client = entityManager.find(Client.class, clientId);
        transaction.commit();
        return client;
    }
}
