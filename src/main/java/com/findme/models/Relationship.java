package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "relationship")
@Table(name = "RELATIONSHIP")
@Getter
@Setter
@ToString
public class Relationship {
    @Id
    @SequenceGenerator(
            name = "RELATIONSHIP_SEQ",
            sequenceName = "RELATIONSHIP_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RELATIONSHIP_SEQ"
    )

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

    @Column(name = "DATE_MODIFY")
    private Date dateModify;
}
