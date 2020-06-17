package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/*
CREATE TABLE POST(
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(100),
DATE_POSTED TIMESTAMP,
USER_ID NUMBER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
 */

@Entity
@Table(name = "POST")
@Getter
@Setter
@ToString
public class Post {
    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ")
    private Long id;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userPosted;
}
