package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.Message;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MessageDAO extends GeneralDAO<Message> {
    @PersistenceContext
    private EntityManager entityManager;

    private final String SELECT_FROM = "SELECT * FROM MESSAGE";
    private final String UPDATE_DATE_DELETED = " update Message m set m.dateDeleted = ? where m in "


    private String alarmMessage = MessageDAO.class.getName();

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

    public List<Message> updateMessages (List<Message> messages) throws InternalServerError {
        try {
            entityManager.createNativeQuery(UPDATE_DATE_DELETED)
                    .setParameter()
        }
    }

    @Transactional
    public List<Message> findAll() throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_FROM, Message.class);
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method findAll() from class "
                    + alarmMessage);
        }
    }
}
