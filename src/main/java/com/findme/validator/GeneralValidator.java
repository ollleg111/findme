package com.findme.validator;

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
    RelationshipStatus status;

    public GeneralValidator(RelationshipStatus status) {
        this.status = status;
    }

    public void setNextValidation(GeneralValidator nextValidation) {
        this.nextValidation = nextValidation;
    }

    public void validationManager(Relationship data, String inputStatus) throws BadRequestException {
        if (inputStatus.equals(status.toString())) {
            check(data, inputStatus);
        }
        if (nextValidation != null) {
            nextValidation.validationManager(data, inputStatus);
        }
    }

    public abstract void check(Relationship data, String inputStatus) throws BadRequestException;
}
