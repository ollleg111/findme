package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/*
CREATE TABLE POST(
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(100),
DATE_POSTED TIMESTAMP,
USER_ID NUMBER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);
 */

/*
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(200) NOT NULL,
DATE_POSTED TIMESTAMP,
LOCATION NVARCHAR2(30),
USER_ID NUMBER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
USER_PAGE_POSTED_ID NUMBER NOT NULL,
CONSTRAINT USER_POST_FK FOREIGN KEY (USER_PAGE_POSTED_ID) REFERENCES USERS(ID)
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

    @NotEmpty(message = "Message text should not be empty")
    @Size(min = 2, max = 200, message = "Message text should be between 2 and 200 characters")
    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    //место прикрепленное к посту (никак не валидируется)
    @Column(name = "LOCATION")
    private String location;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userPosted;

    @ManyToOne
    @JoinColumn(name = "USER_PAGE_POSTED_ID")
    private User userPagePosted;

    //юзеры, которые отмечены в после (аналог фейсбук функции - with user1, user2, user3)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_TAGGED", joinColumns = {@JoinColumn(name = "POST_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> usersTagged;
}

