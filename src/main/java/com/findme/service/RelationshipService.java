package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.exceptions.DaoException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
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

    public void addRelationship(String userIdFrom, String userIdTo) throws DaoException {
        relationshipDAO.addRelationship(userIdFrom, userIdTo);
    }

    public void updateRelationship(String userIdFrom, String userIdTo, String status) throws DaoException {
        relationshipDAO.updateRelationship(userIdFrom, userIdTo, status);
    }

    public List<User> getIn(String userId) throws DaoException {
        return relationshipDAO.getIn(userId);
    }

    public List<User> getOut(String userId) throws DaoException {
        return relationshipDAO.getOut(userId);
    }

    public Relationship getRelationship(String userIdFrom, String userIdTo) throws DaoException {
        return relationshipDAO.getRelationship(userIdFrom, userIdTo);
    }

    public RelationshipStatus getRelationshipStatus(String userIdFrom, String userIdTo) throws DaoException {
        Relationship relationship = getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }
}
