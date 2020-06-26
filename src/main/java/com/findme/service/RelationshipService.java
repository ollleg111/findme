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
    private Relationship relationship;

    @Autowired
    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    public Relationship save(Long userIdFrom, Long userIdTo) throws DaoException, BadRequestException {
        validateUsersId(userIdFrom, userIdTo);
        if (relationshipDAO.getRelationship(userIdFrom, userIdTo) == null) {
            relationship.setUserFrom(userDAO.findById(userIdFrom));
            relationship.setUserTo(userDAO.findById(userIdTo));
            relationship.setRelationshipStatus(RelationshipStatus.WAITING_FOR_ACCEPT);
            return relationshipDAO.save(relationship);
        }
        throw new BadRequestException("You have relationShip status with id: " + userIdTo + " again");
    }

    public Relationship update(Long userIdFrom, Long userIdTo, String status) throws DaoException {
        validateUsersId(userIdFrom, userIdTo);
        Relationship relationship = relationshipDAO.getRelationship(userIdFrom, userIdTo);
        String statusCheck = relationship.getRelationshipStatus().toString();
        if (statusCheck.equals(status)) throw new BadRequestException("You already have status with this people");
        /*
        WAITING_FOR_ACCEPT, REQUEST_REJECTED, FRIENDS, NOT_FRIENDS, DELETED
        */

        if ((status.equals("REQUEST_REJECTED") || status.equals("NOT_FRIENDS") || status.equals("DELETED")) &&
                (statusCheck.equals("WAITING_FOR_ACCEPT") || statusCheck.equals("FRIENDS")))
            throw new BadRequestException("Your request declined");

        if ((status.equals("FRIENDS") && statusCheck.equals("WAITING_FOR_ACCEPT")) ||
                (status.equals("WAITING_FOR_ACCEPT") && statusCheck.equals("FRIENDS"))) {
            relationship.setRelationshipStatus(RelationshipStatus.FRIENDS);
        }
        return relationshipDAO.update(relationship);
    }

    public List<User> getIncome(Long userId) throws DaoException {
        return relationshipDAO.getIn(userId);
    }

    public List<User> getOutcome(Long userId) throws DaoException {
        return relationshipDAO.getOut(userId);
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
        Relationship relationship = relationshipDAO.getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }
}
