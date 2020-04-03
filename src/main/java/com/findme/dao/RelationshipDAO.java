package com.findme.dao;

import com.findme.exceptions.InternalServerError;
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

    private static final String RELATIONSHIP_SAVE = "";
    private static final String RELATIONSHIP_UPDATE = "";
    private static final String RELATIONSHIP_GET = "";
    private static final String RELATIONSHIP_GET_INPUT = "";
    private static final String RELATIONSHIP_GET_OUTPUT = "";

    private String alarmMessage = RelationshipDAO.class.getName();

    @Transactional
    public void addRelationship(String userIdFrom, String userIdTo) throws InternalServerError {
        try {
            int result = entityManager
                    .createNativeQuery(RELATIONSHIP_SAVE)
                    .setParameter(1, Long.valueOf(userIdFrom))
                    .setParameter(2, Long.valueOf(userIdTo))
                    .executeUpdate();

        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " save(String userIdFrom, String userIdTo) from class " + alarmMessage);
        }
    }

    @Transactional
    public void updateRelationship(String userIdFrom, String userIdTo, String status)
            throws InternalServerError {
        try {
            int result = entityManager
                    .createNativeQuery(RELATIONSHIP_UPDATE)
                    .setParameter(1, Long.valueOf(userIdFrom))
                    .setParameter(2, Long.valueOf(userIdTo))
                    .setParameter(2, status.toString())
                    .executeUpdate();

        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " update(String userIdFrom, String userIdTo, String status) from class " + alarmMessage);
        }
    }


    @Transactional
    public Relationship getRelationship(String userIdFrom, String userIdTo) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_UPDATE, Relationship.class);
            query.setParameter(1, Long.valueOf(userIdFrom));
            query.setParameter(2, Long.valueOf(userIdTo));

            return (Relationship) query.getSingleResult();

        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation was filed in method" +
                    " update(String userIdFrom, String userIdTo, String status) from class " + alarmMessage);
        }
    }

    @Transactional
    public List<User> getIn(String userId) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_GET_INPUT, Relationship.class);
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (InternalServerError e) {
            throw new InternalServerError("Operation was filed in method getIn(String userId) from class "
                    + alarmMessage);
        }
    }

    @Transactional
    public List<User> getOut(String userId) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(RELATIONSHIP_GET_OUTPUT, Relationship.class);
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (InternalServerError e) {
            throw new InternalServerError("Operation was filed in method getOut(String userId) from class "
                    + alarmMessage);
        }
    }
}
