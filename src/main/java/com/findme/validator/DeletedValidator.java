package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class DeletedValidator extends GeneralValidator {

    @Autowired
    public DeletedValidator(String status, Relationship relationship) {
        super(status, relationship);
    }

    @Override
    public void check(String inputStatus, Relationship data) throws BadRequestException {
        if (inputStatus.equals(RelationshipStatus.DELETED.toString())) {
            if (data.getRelationshipStatus() != RelationshipStatus.REQUEST_REJECTED)
                throw new BadRequestException("Wrong DELETED status for relationship");

            if (TimeUnit.DAYS.convert((new Date().getTime() - data.getDateModify().getTime()),
                    TimeUnit.MILLISECONDS) < Constants.MIN_DAYS_AS_FRIEND)
                throw new BadRequestException("You cannot DELETE your friend, you must be waiting " +
                        Constants.MIN_DAYS_AS_FRIEND + " days");

            data.setRelationshipStatus(RelationshipStatus.DELETED);
            data.setDateModify(new Date());
            dao.update(data);
        } else {
            throw new BadRequestException("Updating from status " + data.getRelationshipStatus() +
                    " to status " + inputStatus + " doesn't allowed");
        }
    }
}

