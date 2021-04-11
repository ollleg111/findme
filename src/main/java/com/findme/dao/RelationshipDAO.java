package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository

public class RelationshipDAO extends GeneralDAO<Relationship> {
    @PersistenceContext
    private EntityManager entityManager;

    public RelationshipDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Relationship.class);
    }

    private static final String RELATIONSHIP_GET = "SELECT FROM RELATIONSHIP WHERE (USER_FROM_ID = ?1 AND USER_TO_ID = ?2) OR (USER_FROM_ID = ?2 AND USER_TO_ID = ?1)";
    private static final String RELATIONSHIP_GET_INPUT = "SELECT * FROM RELATIONSHIP WHERE STATUS = ?1 AND USER_TO_ID = ?2";
    private static final String RELATIONSHIP_GET_OUTPUT = "SELECT * FROM RELATIONSHIP WHERE STATUS = ?1 AND USER_FROM_ID = ?2";
    private static final String SELECT_FROM = "SELECT * FROM RELATIONSHIP";

    private String alarmMessage = RelationshipDAO.class.getName();

    @Transactional
    public Relationship getRelationship(Long userIdFrom, Long userIdTo) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_GET, Relationship.class);
            query.setParameter(1, userIdFrom);
            query.setParameter(2, userIdTo);
            return (Relationship) query.getSingleResult();
        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " update(String userIdFrom, String userIdTo, String status) from class " + alarmMessage);
        }
    }

    @Transactional
    public List<User> getIn(Long userId) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_GET_INPUT, User.class);
            query.setParameter(1, RelationshipStatus.WAITING_FOR_ACCEPT.toString());
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (InternalServerError e) {
            throw new InternalServerError("Operation was filed in method getIn(String userId) from class "
                    + alarmMessage);
        }
    }

    @Transactional
    public List<User> getOut(Long userId) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_GET_OUTPUT, User.class);
            query.setParameter(1, RelationshipStatus.WAITING_FOR_ACCEPT.toString());
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (InternalServerError e) {
            throw new InternalServerError("Operation was filed in method getOut(String userId) from class "
                    + alarmMessage);
        }
    }

    @Override
    public Relationship findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Relationship save(Relationship relationship) throws DaoException {
        return super.save(relationship);
    }

    @Override
    public Relationship update(Relationship relationship) throws DaoException {
        return super.update(relationship);
    }

    @Override
    public void delete(Relationship relationship) throws DaoException {
        super.delete(relationship);
    }

    @Transactional
    public Integer getRelationsByStatus(Long userIdFrom) throws InternalServerError {
        List<User> users = getIn(userIdFrom);
        if (users.isEmpty())
            return null;
        return users.size();
    }

    @Transactional
    public List<Relationship> findAll() throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_FROM, Relationship.class);
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method findAll() from class "
                    + alarmMessage);
        }
    }
}
