package com.findme.validator;

import com.findme.exceptions.BadRequestException;
import com.findme.models.RelationshipData;

public abstract class GeneralRelationshipValidator {
    private GeneralRelationshipValidator next;

    public GeneralRelationshipValidator chain(GeneralRelationshipValidator next) {
        this.next = next;
        return next;
    }
    public abstract void check(RelationshipData data) throws BadRequestException;
    protected void checkNext(RelationshipData data) throws BadRequestException {
        if (next != null) {
            next.check(data);
        }
    }
}
