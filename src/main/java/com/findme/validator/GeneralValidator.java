package com.findme.validator;

import com.findme.dao.RelationshipDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.models.Relationship;
import org.springframework.stereotype.Component;

/*
https://www.youtube.com/watch?v=66_0-u8P5DQ
https://www.youtube.com/watch?v=HEExe3Bu_2k
 */

@Component
public abstract class GeneralValidator {
    GeneralValidator nextValidation;
    RelationshipDAO dao;
    String status;
    Relationship relationship;

    public RelationshipDAO getDao() {
        return dao;
    }

    public GeneralValidator(String status, Relationship relationship) {
        this.status = status;
        this.relationship = relationship;
    }

    public void setNextValidation(GeneralValidator nextValidation) {
        this.nextValidation = nextValidation;
    }

    public abstract void check(String inputStatus, Relationship data) throws BadRequestException;
}
