CREATE TABLE USERS_TAGGED(
USER_ID NUMERIC NOT NULL,
POST_ID NUMERIC NOT NULL,
PRIMARY KEY (USER_ID, POST_ID),
CONSTRAINT USER_ID_FK FOREIGN KEY(USER_ID) REFERENCES USERS(ID),
CONSTRAINT POST_ID_FK FOREIGN KEY(POST_ID) REFERENCES POST(ID)
);