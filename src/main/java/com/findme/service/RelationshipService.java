package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
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
    private UserDAO userDAO;

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    public void addRelationship(Long userIdFrom, Long userIdTo) throws DaoException {
        validateUsersId(userIdFrom, userIdTo);
        if (relationshipDAO.getRelationship(userIdFrom, userIdTo) == null) {
            relationshipDAO.addRelationship(userIdFrom, userIdTo, RelationshipStatus.WAITING_FOR_ACCEPT);
        } else {
            throw new BadRequestException("You have had relationShip with id: " + userIdTo + " already");
        }
    }

    public void updateRelationship(Long userIdFrom, Long userIdTo, String status) throws DaoException {
        validateUsersId(userIdFrom, userIdTo);

        /*
        WAITING_FOR_ACCEPT, REQUEST_REJECTED, FRIENDS, NOT_FRIENDS, DELETED
         */

        String statusCheck = relationshipDAO.getRelationship(userIdFrom,userIdTo).getRelationshipStatus().toString();
        if(statusCheck.equals(status)) throw new BadRequestException("You have some status");
        if(statusCheck.equals("REQUEST_REJECTED")) throw new BadRequestException("Your request declined");

        relationshipDAO.updateRelationship(userIdFrom, userIdTo, status);
    }

    public List<User> getIn(Long userId) throws DaoException {
        return relationshipDAO.getIn(userId);
    }

    public List<User> getOut(Long userId) throws DaoException {
        return relationshipDAO.getOut(userId);
    }

    public Relationship getRelationship(Long userIdFrom, Long userIdTo) throws DaoException {
        return relationshipDAO.getRelationship(userIdFrom, userIdTo);
    }

    public boolean validateUsersId(Long userIdFrom, Long userIdTo) throws BadRequestException {
        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You cannot add relationship to yourself");
        if (userIdTo <= 0)
            throw new BadRequestException("You write wrong user id");
        if (userDAO.findById(userIdTo) == null)
            throw new BadRequestException("User with id: " + userIdTo + " does not exist");
        return true;
    }

    public RelationshipStatus getStatus(Long userIdFrom, Long userIdTo) throws DaoException {
        Relationship relationship = getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }
}
