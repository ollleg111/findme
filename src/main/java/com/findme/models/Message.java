package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/*
CREATE TABLE MESSAGE(
ID NUMBER PRIMARY KEY,
TEXT NVARCHAR2(100),
DATE_SENT TIMESTAMP,
DATE_READ TIMESTAMP,
USER_FROM_ID NUMBER NOT NULL,
CONSTRAINT USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER NOT NULL,
CONSTRAINT USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID)
);
 */

@Entity
@Table(name = "MESSAGE")
@Getter
@Setter
@ToString
public class Message {
    @Id
    @SequenceGenerator(name = "MESSAGES_SEQ", sequenceName = "MESSAGES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGES_SEQ")
    private Long id;

    @NotEmpty(message = "Message text should not be empty")
    @Size(min = 2, max = 100, message = "Message text should be between 2 and 100 characters")
    @Column(name = "TEXT")
    private String text;

    @Column(name = "DATE_SENT")
    private Date dateSent;

    @Column(name = "DATE_READ")
    private Date dateRead;

    @ManyToOne
    @JoinColumn(name = "USER_FROM_ID", nullable = false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "USER_TO_ID", nullable = false)
    private User userTo;
}
