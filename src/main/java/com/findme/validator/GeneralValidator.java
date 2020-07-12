package com.findme.validator;

import com.findme.dao.RelationshipDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import org.springframework.stereotype.Component;

/*
https://www.youtube.com/watch?v=66_0-u8P5DQ
https://www.youtube.com/watch?v=HEExe3Bu_2k
 */

@Component
public abstract class GeneralValidator {
    private GeneralValidator nextValidation;
    private RelationshipDAO dao;
    RelationshipStatus status;

    public GeneralValidator(RelationshipStatus status) {
        this.status = status;
    }

    public GeneralValidator(RelationshipDAO dao, RelationshipStatus status) {
        this.dao = dao;
        this.status = status;
    }

    public void setNextValidation(GeneralValidator nextValidation) {
        this.nextValidation = nextValidation;
    }

    public abstract void check(Relationship data, String inputStatus) throws BadRequestException;
}
