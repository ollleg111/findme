package com.findme.models;

import javax.persistence.*;

/*
CREATE TABLE RELATIONSHIP(
ID NUMBER PRIMARY KEY,
USER_FROM_ID NUMBER,
CONSTRAINT RELATIONSHIP_USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER,
CONSTRAINT RELATIONSHIP_USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID),
STATUS NVARCHAR2(20), CHECK(STATUS IN('NEW','WAITING_FOR_ACCEPT','FRIENDS','NOT_FRIENDS'))
);
 */

@Entity
@Table(name = "RELATIONSHIP")
public class Relationship {
    @Id
    @SequenceGenerator(name = "RELATIONSHIP_SEQ", sequenceName = "RELATIONSHIP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIONSHIP_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_FROM_ID")
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "USER_TO_ID")
    private User userTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private RelationshipStatus relationshipStatus;
}
