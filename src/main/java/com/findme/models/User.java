package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/*
CREATE TABLE USERS(
ID NUMBER PRIMARY KEY,
FIRST_NAME NVARCHAR2(50) NOT NULL,
LAST_NAME NVARCHAR2(50) NOT NULL,
PHONE_NUMBER NVARCHAR2(15) NOT NULL,
E_MAIL NVARCHAR2(50) NOT NULL,
COUNTRY NVARCHAR2(50) NOT NULL,
CITY NVARCHAR2(50) NOT NULL,
AGE NUMBER NOT NULL,
DATE_REGISTERED TIMESTAMP,
DATE_LAST_ACTIVE TIMESTAMP,
RELATIONSHIP_STATUS NVARCHAR2(12) DEFAULT 'SINGLE',
CHECK(RELATIONSHIP_STATUS = 'MARRIED' OR RELATIONSHIP_STATUS = 'SINGLE'),
RELIGION NVARCHAR2(12) DEFAULT 'CHRISTIAN',
CHECK(RELIGION IN('CHRISTIAN','BUDDHIST','CATHOLIC','MUSLIM')),
SCHOOL NVARCHAR2(50) NOT NULL,
UNIVERSITY NVARCHAR2(50) NOT NULL
);
 */

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString
public class User {
    @Id
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    //TODO from existed data

    @Column(name = "E_MAIL")
    private String mail;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    private Date dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    private Date dateLastActive;

    @Column(name = "RELATIONSHIP_STATUS")
    private String relationshipStatus;

    @Column(name = "RELIGION")
    private String religion;

    //TODO from existed data
    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "UNIVERSITY")
    private String university;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFrom")
    private List<Message> messagesSent;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTo")
    private List<Message> messagesReceived;

//    private String[] interests;
}
