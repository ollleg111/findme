package com.findme.models;

import javax.persistence.*;

/*
CREATE TABLE RELATIONSHIP(
ID NUMBER PRIMARY KEY,
USER_FROM_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID),
STATUS NVARCHAR2(20), CHECK(STATUS IN('WAITING_FOR_ACCEPT','REQUEST_REJECTED','FRIENDS','NOT_FRIENDS','DELETED'))
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

    public Long getId() {
        return id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", userFrom=" + userFrom +
                ", userTo=" + userTo +
                ", relationshipStatus=" + relationshipStatus +
                '}';
    }
}
