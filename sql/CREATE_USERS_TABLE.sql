/*
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    //TODO from existed data
    private String mail;
    private String password;
    private String country;
    private String city;

    private Integer age;
    private Date dateRegistered;
    private Date dateLastActive;
    //TODO enum
    private String relationshipStatus;
    private String religion;
    //TODO from existed data
    private String school;
    private String university;

    private List<Message> messagesSent;
    private List<Message> messagesReceived;
 */

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

//OR

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
DATE_REGISTERED TIMESTAMP,
DATE_LAST_ACTIVE TIMESTAMP,
RELATIONSHIP_STATUS NVARCHAR2(12) DEFAULT 'SINGLE',
CHECK(RELATIONSHIP_STATUS = 'MARRIED' OR RELATIONSHIP_STATUS = 'SINGLE'),
RELIGION NVARCHAR2(12) DEFAULT 'CHRISTIAN',
CHECK(RELIGION IN('CHRISTIAN','BUDDHIST','CATHOLIC','MUSLIM')),
SCHOOL NVARCHAR2(50) NOT NULL,
UNIVERSITY NVARCHAR2(50) NOT NULL
);

CREATE SEQUENCE USERS_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;

----------------------------------------------change to postgres

CREATE TABLE users (
    id numeric,
    CONSTRAINT user_pk PRIMARY KEY (id),
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone_number varchar(15) not null,
    e_mail varchar(50) not null,
    password varchar(50) not null,
    country varchar(50) not null,
    city varchar(50) not null,
    age numeric(3,0) not null,
    date_registered TIMESTAMP WITH TIME ZONE,
    date_last_active TIMESTAMP WITH TIME ZONE,
    relationship_status varchar(12) default 'SINGLE',
    check (relationship_status = 'MARRIED' or relationship_status = 'SINGLE'),
    religion varchar(50), check (religion in ('CHRISTIAN','BUDDHIST','CATHOLIC','MUSLIM')),
    school varchar(50) not null,
    university varchar(50) not null
);

