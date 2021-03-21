- all for Oracle DB -

DROP TABLE USERS;

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

DROP TABLE POST;

CREATE TABLE POST(
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(200),
DATE_POSTED TIMESTAMP,
LOCATION NVARCHAR2(30),
USER_ID NUMBER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
USER_PAGE_POSTED_ID NUMBER NOT NULL,
CONSTRAINT USER_POST_FK FOREIGN KEY (USER_PAGE_POSTED_ID) REFERENCES USERS(ID)
);

DROP TABLE MESSAGE;

CREATE TABLE MESSAGE(
ID NUMBER PRIMARY KEY,
TEXT NVARCHAR2(100) NOT NULL,
DATE_SENT TIMESTAMP,
DATE_READ TIMESTAMP,
USER_FROM_ID NUMBER NOT NULL,
CONSTRAINT USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER NOT NULL,
CONSTRAINT USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID)
);

CREATE TABLE USERS_TAGGED(
USER_ID NUMERIC NOT NULL,
POST_ID NUMERIC NOT NULL,
PRIMARY KEY (USER_ID, POST_ID),
CONSTRAINT USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USERS(ID),
CONSTRAINT POST_ID_FK FOREIGN KEY(POST_ID) REFERENCES POST(ID)
);

DROP TABLE RELATIONSHIP;

CREATE TABLE RELATIONSHIP(
ID NUMBER PRIMARY KEY,
USER_FROM_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID),
STATUS NVARCHAR2(20), CHECK(STATUS IN('NEW','WAITING_FOR_ACCEPT','REQUEST_REJECTED','FRIENDS','NOT FRIENDS')),
DATE_MODIFY TIMESTAMP
);

CREATE SEQUENCE POST_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE USERS_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE MESSAGES_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE RELATIONSHIP_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;

////////////////////////////////////////////////
 - all for Postgre DB -

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

CREATE TABLE post (
    id numeric,
    CONSTRAINT post_pk PRIMARY KEY (id),
    message varchar(200),
    date_posted TIMESTAMP WITH TIME ZONE,
    location varchar(30),
    user_id numeric not null,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    user_posted_id numeric not null,
    CONSTRAINT user_posted_id_fk FOREIGN KEY (user_posted_id) REFERENCES users (id)
);

CREATE TABLE message (
    id numeric,
    CONSTRAINT message_pk PRIMARY KEY (id),
    text varchar(200) not null,
    date_sent TIMESTAMP WITH TIME ZONE,
    date_read TIMESTAMP WITH TIME ZONE,
    user_from_id numeric not null,
    CONSTRAINT user_from_id_fk FOREIGN KEY (user_from_id) REFERENCES users (id),
    user_to_id numeric not null,
    CONSTRAINT user_to_id_fk FOREIGN KEY (user_to_id) REFERENCES users (id)
);

CREATE TABLE users_tagged (
    user_id numeric not null,
    post_id numeric not null,
    PRIMARY KEY (user_id,post_id),
    CONSTRAINT user_id_fk FOREIGN KEY(user_id) REFERENCES users (id),
    CONSTRAINT post_id_fk FOREIGN KEY(post_id) REFERENCES post (id)
);

CREATE TABLE relationship (
    id numeric,
    CONSTRAINT relationship_pk PRIMARY KEY (id),
    user_from_id numeric not null,
    CONSTRAINT relationship_user_from_fk FOREIGN KEY (user_from_id) REFERENCES users (id),
    user_to_id numeric not null,
    CONSTRAINT relationship_user_to_fk FOREIGN KEY (user_to_id) REFERENCES users (id),
    status varchar(20), check(status IN('NEW','WAITING_FOR_ACCEPT','REQUEST_REJECTED','FRIENDS','NOT FRIENDS')),
    date_modify TIMESTAMP WITH TIME ZONE
);

CREATE SEQUENCE POST_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE USERS_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE MESSAGES_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;
CREATE SEQUENCE RELATIONSHIP_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;

INSERT INTO public.users(
	id, first_name, last_name, phone_number, e_mail, password, country, city, age, date_registered, date_last_active, relationship_status, religion, school, university)
	VALUES (1, 'AAAA', 'AAAA', '12345678', 'aaaa@gmail.com', 'AAAA', 'AAAA', 'AAAA', 25, '10-10-10', '10-10-10', 'SINGLE', 'CHRISTIAN', 'AAAA', 'AAAA');
INSERT INTO public.users(
	id, first_name, last_name, phone_number, e_mail, password, country, city, age, date_registered, date_last_active, relationship_status, religion, school, university)
	VALUES (2, 'BBBB', 'BBBB', '12345678', 'BBBB@gmail.com', 'BBBB', 'BBBB', 'BBBB', 25, '10-10-10', '10-10-10', 'SINGLE', 'CHRISTIAN', 'BBBB', 'BBBB');
INSERT INTO public.users(
	id, first_name, last_name, phone_number, e_mail, password, country, city, age, date_registered, date_last_active, relationship_status, religion, school, university)
	VALUES (3, 'CCCC', 'CCCC', '12345678', 'CCCC@gmail.com', 'CCCC', 'CCCC', 'CCCC', 25, '10-10-10', '10-10-10', 'SINGLE', 'CHRISTIAN', 'CCCC', 'CCCC');UES ('CCCC', 'CCCC', '12345678', 'CCCC@gmail.com', 'CCCC', 'CCCC', 'CCCC', 25, '10-10-10', '10-10-10', 'SINGLE', 'CHRISTIAN', 'CCCC', 'CCCC');