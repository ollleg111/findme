package com.findme.service;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.util.Constants;
import com.findme.validator.GeneralRelationshipValidator;
import com.findme.validator.WaitingRelationshipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        /*
        максимальное ко-во исходящих заявок в друзья для одного пользователя - 10
         */
        Integer quantity = relationshipDAO.getRelationsByStatus(userIdFrom);
        if (quantity != null && quantity >= Constants.MAX_QUANTITY_OF_STATUS_WAITING_FOR_ACCEPT)
            throw new BadRequestException("Too much requests WAITING_FOR_ACCEPT. You have only 10 requests");

        if (relationshipDAO.getRelationship(userIdFrom, userIdTo) != null) throw
                new BadRequestException("You have relationShip status with id: " + userIdTo + " again");

        relationship.setUserFrom(userDAO.findById(userIdFrom));
        relationship.setUserTo(userDAO.findById(userIdTo));
        relationship.setRelationshipStatus(RelationshipStatus.WAITING_FOR_ACCEPT);
        relationship.setDateModify(new Date());

        return relationshipDAO.save(relationship);
    }

    public Relationship update(Long userIdFrom, Long userIdTo, String status) throws DaoException {

        validateUsersId(userIdFrom, userIdTo);
        checkUpdateValidation(relationshipDAO.getRelationship(userIdFrom,userIdTo));

        /*
        Relationship relationship = relationshipDAO.getRelationship(userIdFrom, userIdTo);
        String statusCheck = relationship.getRelationshipStatus().toString();
        if (statusCheck.equals(status)) throw new BadRequestException("You already have status with this people");
        if ((status.equals("REQUEST_REJECTED") || status.equals("NOT_FRIENDS") || status.equals("DELETED")) &&
                (statusCheck.equals("WAITING_FOR_ACCEPT") || statusCheck.equals("FRIENDS")))
            throw new BadRequestException("Your request declined");
        if ((status.equals("FRIENDS") && statusCheck.equals("WAITING_FOR_ACCEPT")) ||
                (status.equals("WAITING_FOR_ACCEPT") && statusCheck.equals("FRIENDS"))) {
            relationship.setRelationshipStatus(RelationshipStatus.FRIENDS);
        }
        */

        relationship.setDateModify(new Date());
        return relationshipDAO.update(relationship);
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
        Relationship relationship = relationshipDAO.getRelationship(userIdFrom, userIdTo);
        return relationship == null ? null : relationship.getRelationshipStatus();
    }

    private void checkUpdateValidation(Relationship relationship) {
        GeneralRelationshipValidator general = new WaitingRelationshipValidator()
    }
}
