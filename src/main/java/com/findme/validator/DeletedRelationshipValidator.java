package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.RelationshipData;
import com.findme.models.RelationshipStatus;
import com.findme.util.Constants;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeletedRelationshipValidator extends GeneralRelationshipValidator {

    @Override
    public void check(RelationshipData data) throws BadRequestException {
        if (data.getNextStatus().equals(RelationshipStatus.DELETED)) {
            if (!data.getCurrentStatus().equals(RelationshipStatus.FRIENDS)) {
                throw new BadRequestException("Incorrect new DELETED status for relationship");
            }

            if (TimeUnit.DAYS.
                    convert((new Date().getTime() - data.getDateModify().getTime()), TimeUnit.MILLISECONDS) <
                    Constants.MIN_DAYS_AS_FRIEND) {
                throw new BadRequestException("You must be wait " +
                        Constants.MIN_DAYS_AS_FRIEND + " days for delete");
            }
        }
        checkNext(data);
    }
}

