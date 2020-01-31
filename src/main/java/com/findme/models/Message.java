package com.findme.models;

import javax.persistence.*;
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
public class Message {
    @Id
    @SequenceGenerator(name = "MESSAGES_SEQ", sequenceName = "MESSAGES_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGES_SEQ")
    private Long id;

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

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public User getUserTo() {
        return userTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", dateSent=" + dateSent +
                ", dateRead=" + dateRead +
                ", userFrom=" + userFrom +
                ", userTo=" + userTo +
                '}';
    }
}
