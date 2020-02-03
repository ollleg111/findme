package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.models.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageDAO extends GeneralDAO<Message> {
    @PersistenceContext
    private EntityManager entityManager;

    public MessageDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Message.class);
    }

    @Override
    public Message findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Message save(Message message) throws DaoException {
        return super.save(message);
    }

    @Override
    public Message update(Message message) throws DaoException {
        return super.update(message);
    }

    @Override
    public void delete(Message message) throws DaoException {
        super.delete(message);
    }

    @Override
    public List<Message> findAll() throws DaoException {
        return super.findAll();
    }
}
