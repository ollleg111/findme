package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/*
CREATE TABLE USERS(
ID NUMBER PRIMARY KEY,
FIRST_NAME NVARCHAR2(50) NOT NULL,
LAST_NAME NVARCHAR2(50) NOT NULL,
PHONE_NUMBER NVARCHAR2(15) NOT NULL,
E_MAIL NVARCHAR2(50) NOT NULL,
PASSWORD NVARCHAR2(50) NOT NULL,
COUNTRY NVARCHAR2(50) NOT NULL,
CITY NVARCHAR2(50) NOT NULL,
AGE NUMBER NOT NULL,
DATE_REGISTERED DATE NOT NULL,
DATE_LAST_ACTIVE DATE NOT NULL,
RELATIONSHIP_STATUS NVARCHAR2(50), CHECK(RELATIONSHIP_STATUS IN('MARRIED','SINGLE')),
RELIGION NVARCHAR2(50), CHECK(RELIGION IN('CHRISTIAN','BUDDHIST','CATHOLIC','MUSLIM')),
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

    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotEmpty(message = "Phone number should not be empty")
    @Size(min = 10, max = 15, message = "Name should be between 10 and 15 characters")
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    //TODO from existed data

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "E_MAIL")
    private String mail;

    @NotEmpty(message = "Password number should not be empty")
    @Size(min = 6, max = 50, message = "Password should be between 6 and 50 characters")
    @Column(name = "PASSWORD")
    private String password;

    @NotEmpty(message = "Country should not be empty")
    @Size(min = 2, max = 50, message = "Country should be between 2 and 50 characters")
    @Column(name = "COUNTRY")
    private String country;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 4, max = 50, message = "City should be between 6 and 50 characters")
    @Column(name = "CITY")
    private String city;

    @NotEmpty(message = "Age should not be empty")
    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "AGE")
    private Integer age;

    @DateTimeFormat
    @Column(name = "DATE_REGISTERED")
    private Date dateRegistered;

    @DateTimeFormat
    @Column(name = "DATE_LAST_ACTIVE")
    private Date dateLastActive;

    @Column(name = "RELATIONSHIP_STATUS")
    private String relationshipStatus;

    @Column(name = "RELIGION")
    private String religion;

    //TODO from existed data
    @NotEmpty(message = "School should not be empty")
    @Size(min = 2, max = 50, message = "School should be between 2 and 50 characters")
    @Column(name = "SCHOOL")
    private String school;

    @NotEmpty(message = "University should not be empty")
    @Size(min = 4, max = 50, message = "University should be between 4 and 50 characters")
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
