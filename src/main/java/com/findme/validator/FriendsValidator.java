package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FriendsValidator extends GeneralValidator {

    @Autowired
    public FriendsValidator(String status, Relationship relationship) {
        super(status, relationship);
    }

    @Override
    public void check(String inputStatus, Relationship data) throws BadRequestException {
        if (inputStatus.equals(RelationshipStatus.FRIENDS.toString())) {
            if (data.getRelationshipStatus() != RelationshipStatus.WAITING_FOR_ACCEPT)
                throw new BadRequestException("Wrong FRIENDS status for relationship");

            Integer quantity = dao.getRelationsByStatus(data.getUserFrom().getId());
            if (quantity != null && quantity >= Constants.LIMIT_OF_FRIENDS)
                throw new BadRequestException("You have limit of friends = " + Constants.LIMIT_OF_FRIENDS);

            data.setRelationshipStatus(RelationshipStatus.FRIENDS);
            data.setDateModify(new Date());
            dao.update(data);
        } else {
            this.check(inputStatus, data);
        }
    }
}
