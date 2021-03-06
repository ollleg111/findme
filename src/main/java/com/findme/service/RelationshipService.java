package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.util.Constants;
import com.findme.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RelationshipService {
    private final RelationshipDAO relationshipDAO;
    private final UserDAO userDAO;
    private Relationship relationship;

    @Autowired
    public Relationship getRelationship() {
        return relationship;
    }

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }

    public Relationship save(Long userIdFrom, Long userIdTo) throws DaoException, BadRequestException {

        validateUsersId(userIdFrom, userIdTo);
        Integer quantity = relationshipDAO.getRelationsByStatus(userIdFrom);
        if (quantity != null && quantity >= Constants.MAX_QUANTITY_OF_STATUS_WAITING_FOR_ACCEPT)
            throw new BadRequestException("Too much requests WAITING_FOR_ACCEPT. You have only 10 requests");

        if (getRelationship(userIdFrom, userIdTo) != null) throw
                new BadRequestException("You have relationShip status with id: " + userIdTo + " again");

        relationship.setUserFrom(userDAO.findById(userIdFrom));
        relationship.setUserTo(userDAO.findById(userIdTo));
        relationship.setRelationshipStatus(RelationshipStatus.WAITING_FOR_ACCEPT);
        relationship.setDateModify(new Date());

        return relationshipDAO.save(relationship);
    }

    public Relationship update(Long userIdFrom, Long userIdTo, String status) throws DaoException {
        validateUsersId(userIdFrom, userIdTo);
        Relationship relationUpdate = getRelationship(userIdFrom, userIdTo);
        initCheck(status, relationUpdate);
        return relationUpdate;
    }

    public List<User> getIncome(Long userId) throws DaoException {
        return relationshipDAO.getIn(userId);
    }

    public List<User> getOutcome(Long userId) throws DaoException {
        return relationshipDAO.getOut(userId);
    }

    private void validateUsersId(Long userIdFrom, Long userIdTo) throws BadRequestException {
        if (userIdFrom.equals(userIdTo))
            throw new BadRequestException("You cannot add relationship to yourself");
        if (userIdTo <= 0)
            throw new BadRequestException("You write wrong user id");
        if (userDAO.findById(userIdTo) == null)
            throw new BadRequestException("User with id: " + userIdTo + " does not exist");
    }

    public RelationshipStatus getStatus(Long userIdFrom, Long userIdTo) throws DaoException {
        Relationship relationship = getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }

    public Relationship getRelationship(Long userIdFrom, Long userIdTo) throws DaoException {
        return relationshipDAO.getRelationship(userIdFrom, userIdTo);
    }
    private void initCheck(String status, Relationship relationship) {
        GeneralValidator waitingValidator = new WaitingValidator(status, relationship);
        GeneralValidator rejectedValidator = new RejectedValidator(status, relationship);
        GeneralValidator friendsValidator = new FriendsValidator(status, relationship);
        GeneralValidator notFriendsValidator = new NotFriendValidator(status, relationship);
        GeneralValidator deletedValidator = new DeletedValidator(status, relationship);

        waitingValidator.check(status, relationship);

        waitingValidator.setNextValidation(rejectedValidator);
        rejectedValidator.setNextValidation(friendsValidator);
        friendsValidator.setNextValidation(notFriendsValidator);
        notFriendsValidator.setNextValidation(deletedValidator);
    }
}
