package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.models.Message;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class MessageDAO extends GeneralDAO<Message> {
    @PersistenceContext
    private EntityManager entityManager;

    private final String UPDATE_ALL_DATE_DELETED = "UPDATE MESSAGE SET MESSAGE.dateDeleted = ?1 WHERE " +
            "((MESSAGE.userFromId.id =?2 AND MESSAGE.userToId.id =?3) OR (MESSAGE.userToId.id =?3 AND MESSAGE.userFromId.id =?2))";
    private final String UPDATE_DATE_DELETED = "UPDATE MESSAGE SET MESSAGE.dateDeleted = ?1 WHERE MESSAGE.ID  =?2";
    private final String SELECT_MESSAGES = "SELECT * FROM MESSAGE WHERE " +
            "((MESSAGE.userFromId.id =?1 AND MESSAGE.userToId.id =?2) OR (MESSAGE.userToId.id =?2 AND MESSAGE.userFromId.id =?1))";

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

    public void updateAllMessagesToUser(Long userFromId, Long userToId) throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(UPDATE_ALL_DATE_DELETED);
            query.setParameter(1, new Date());
            query.setParameter(2, userFromId);
            query.setParameter(3, userToId);
            query.executeUpdate();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method" +
                    " updateAllMessagesToUser(Long userFromId, Long userToId) from class " + alarmMessage);
        }
    }

    public void updateListMessages(List<Long> messagesId) throws DaoException {
        try {
            for (Long id : messagesId){
                Query query = entityManager.createNativeQuery(UPDATE_DATE_DELETED);
                query.setParameter(1, new Date());
                query.setParameter(2, id);
                query.executeUpdate();
            }
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method updateMessages(List<Message> messages) " +
                    "from class " + alarmMessage);
        }
    }

    public List<Message> getMessages(Long userFromId, Long userToId, Integer index) {
        try {
            Query query = entityManager.createNativeQuery(SELECT_MESSAGES, Message.class);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.setFirstResult(index);
            query.setMaxResults(20);
             /*
            Сообщения должны подгружаться батчами по 20
             */
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method" +
                    " getMessages(Long userFromId, Long userToId, Integer index) from class "
                    + alarmMessage);
        }
    }
}
