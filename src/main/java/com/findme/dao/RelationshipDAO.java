package com.findme.dao;

import com.findme.exceptions.InternalServerError;
import com.findme.models.Relationship;
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
    public void addRelationship(Long userIdFrom, Long userIdTo) throws InternalServerError {
        try {
            int result = entityManager
                    .createNativeQuery(RELATIONSHIP_SAVE)
                    .setParameter(1, userIdFrom)
                    .setParameter(2, userIdTo)
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
                    .setParameter(2, status)
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
    public List<Relationship> getIn(Long userId) throws InternalServerError {
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
    public List<Relationship> getOut(Long userId) throws InternalServerError {
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
