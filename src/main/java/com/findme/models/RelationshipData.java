package com.findme.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder

public class RelationshipData {
    private RelationshipStatus currentStatus;
    private RelationshipStatus nextStatus;
    private Integer friendsCount;
    private Integer requestCount;
    private Date dateModify;
}