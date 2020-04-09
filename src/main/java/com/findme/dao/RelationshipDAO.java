package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.Message;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
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

    private static final String RELATIONSHIP_ADD = "INSERT INTO RELATIONSHIP(USER_FROM_ID,USER_TO_ID,STATUS) VALUES(?,?,?)";
    private static final String RELATIONSHIP_UPDATE = "UPDATE RELATIONSHIP SET USER_FROM_ID = ?,USER_TO_ID = ?,STATUS = ?";
    private static final String RELATIONSHIP_GET = "SELECT FROM RELATIONSHIP WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";
    private static final String RELATIONSHIP_GET_INPUT = "SELECT r.userFrom FROM RELATIONSHIP r WHERE r.STATUS = 'WAITING_FOR_ACCEPT' AND r.userTo.id = ?";
    private static final String RELATIONSHIP_GET_OUTPUT = "SELECT r.userTo FROM RELATIONSHIP r WHERE r.STATUS = 'WAITING_FOR_ACCEPT' AND r.userFrom.id = ?";

    private String alarmMessage = RelationshipDAO.class.getName();

    @Transactional
    public void addRelationship(Long userIdFrom, Long userIdTo, RelationshipStatus status) throws InternalServerError {
        try {
            int result = entityManager
                    .createNativeQuery(RELATIONSHIP_ADD)
                    .setParameter(1, userIdFrom)
                    .setParameter(2, userIdTo)
                    .setParameter(3, status.toString())
                    .executeUpdate();

        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " save(String userIdFrom, String userIdTo) from class " + alarmMessage);
        }
    }

    @Transactional
    public void updateRelationship(Long userIdFrom, Long userIdTo, String status)
            throws InternalServerError {
        try {
            int result = entityManager
                    .createNativeQuery(RELATIONSHIP_UPDATE)
                    .setParameter(1, userIdFrom)
                    .setParameter(2, userIdTo)
                    .setParameter(3, status)
                    .executeUpdate();

        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " update(String userIdFrom, String userIdTo, String status) from class " + alarmMessage);
        }
    }

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

    @Override
    public List<Relationship> findAll() throws DaoException {
        return super.findAll();
    }
}
