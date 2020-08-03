package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WaitingValidator extends GeneralValidator {

    @Autowired
    public WaitingValidator(String status, Relationship relationship) {
        super(status, relationship);
    }

    @Override
    public void check(String inputStatus, Relationship data) throws BadRequestException {
        if (inputStatus.equals(RelationshipStatus.WAITING_FOR_ACCEPT.toString())) {
            if (data.getRelationshipStatus().toString().equals(inputStatus) ||
                    data.getRelationshipStatus() == RelationshipStatus.FRIENDS)
                throw new BadRequestException("Updating doesn't allowed");

            data.setRelationshipStatus(RelationshipStatus.WAITING_FOR_ACCEPT);
            data.setDateModify(new Date());
            dao.update(data);
        } else {
            this.check(inputStatus, data);
        }
    }
}
