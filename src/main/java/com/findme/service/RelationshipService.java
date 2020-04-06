package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.exceptions.DaoException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {
    private RelationshipDAO relationshipDAO;

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    public void addRelationship(Long userIdFrom, Long userIdTo) throws DaoException {
        relationshipDAO.addRelationship(userIdFrom, userIdTo);
    }

    public void updateRelationship(Long userIdFrom, Long userIdTo, String status) throws DaoException {
        relationshipDAO.updateRelationship(userIdFrom, userIdTo, status);
    }

    public List<Relationship> getIn(Long userId) throws DaoException {
        return relationshipDAO.getIn(userId);
    }

    public List<Relationship> getOut(Long userId) throws DaoException {
        return relationshipDAO.getOut(userId);
    }

    public Relationship getRelationship(Long userIdFrom, Long userIdTo) throws DaoException {
        return relationshipDAO.getRelationship(userIdFrom, userIdTo);
    }

    public RelationshipStatus getStatus(Long userIdFrom, Long userIdTo) throws DaoException {
        Relationship relationship = getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }
}
