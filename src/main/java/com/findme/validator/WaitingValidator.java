package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WaitingValidator extends GeneralValidator {

    private RejectedValidator rejectedValidator;

    @Autowired
    public RejectedValidator getRejectedValidator() {
        return rejectedValidator;
    }

    public WaitingValidator(RelationshipStatus status) {
        super(status);
    }

    @Override
    public void check(Relationship data, String inputStatus) throws BadRequestException {
        if (inputStatus.equals(status.toString())) {
            if (data.getRelationshipStatus() == status || data.getRelationshipStatus() == RelationshipStatus.FRIENDS)
                throw new BadRequestException("Updating does n`t allowed");

            data.setRelationshipStatus(status);
            data.setDateModify(new Date());
            dao.update(data);
        } else {
            rejectedValidator.check(data, inputStatus);
        }
    }
}
