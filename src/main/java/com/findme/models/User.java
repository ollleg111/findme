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


@Entity(name = "users")
@Table(name = "USERS")
@Getter
@Setter
@ToString
public class User {
    @Id
    @SequenceGenerator(
            name = "USERS_SEQ",
            sequenceName = "USERS_SEQ",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "USERS_SEQ"
    )
    private Long id;

    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "FIRST_NAME", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "LAST_NAME", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @NotEmpty(message = "Phone number should not be empty")
    @Size(min = 10, max = 15, message = "Name should be between 10 and 15 characters")
    @Column(name = "PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "E_MAIL", nullable = false, columnDefinition = "TEXT")
    private String mail;

    @NotEmpty(message = "Password number should not be empty")
    @Size(min = 6, max = 50, message = "Password should be between 6 and 50 characters")
    @Column(name = "PASSWORD", nullable = false, columnDefinition = "TEXT")
    private String password;

    @NotEmpty(message = "Country should not be empty")
    @Size(min = 2, max = 50, message = "Country should be between 2 and 50 characters")
    @Column(name = "COUNTRY", nullable = false, columnDefinition = "TEXT")
    private String country;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 4, max = 50, message = "City should be between 6 and 50 characters")
    @Column(name = "CITY", nullable = false, columnDefinition = "TEXT")
    private String city;

    @NotEmpty(message = "Age should not be empty")
    @Min(value = 0, message = "Age should be greater than 0")
    @Column(name = "AGE", nullable = false)
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
}
