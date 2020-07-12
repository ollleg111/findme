package com.findme.validator;

import com.findme.dao.RelationshipDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WaitingValidator extends GeneralValidator {
    private RelationshipDAO dao;

    public WaitingValidator(RelationshipStatus status) {
        super(status);
    }

    public WaitingValidator(RelationshipStatus status, RelationshipDAO dao) {
        super(status);
        this.dao = dao;
    }

    @Override
    public void check(Relationship data, String inputStatus) throws BadRequestException {
        if(inputStatus.equals(status.toString())){
            if(data.getRelationshipStatus() == status || data.getRelationshipStatus() == RelationshipStatus.FRIENDS)
                throw new BadRequestException("Updating doesn`t allowed");

            data.setRelationshipStatus(status);
            data.setDateModify(new Date());
            dao.update(data);
        }
    }
}
