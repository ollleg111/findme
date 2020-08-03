package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RejectedValidator extends GeneralValidator {

    @Autowired
    public RejectedValidator(String status, Relationship relationship) {
        super(status, relationship);
    }

    @Override
    public void check(String inputStatus, Relationship data) throws BadRequestException {
        if (inputStatus.equals(RelationshipStatus.REQUEST_REJECTED.toString()) &&
                data.getRelationshipStatus() == RelationshipStatus.WAITING_FOR_ACCEPT) {

            data.setRelationshipStatus(RelationshipStatus.REQUEST_REJECTED);
            data.setDateModify(new Date());
            dao.update(data);
        } else {
            this.check(inputStatus, data);
        }
    }
}
