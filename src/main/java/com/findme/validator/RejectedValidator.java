package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RejectedValidator extends GeneralValidator {

    public RejectedValidator(RelationshipStatus status) {
        super(status);
    }

    @Override
    public void check(Relationship data, String inputStatus) throws BadRequestException {
        if (inputStatus.equals(status.toString()) &&
                data.getRelationshipStatus() == RelationshipStatus.WAITING_FOR_ACCEPT) {

            data.setRelationshipStatus(status);
            data.setDateModify(new Date());
            dao.update(data);
        }
    }
}
