CREATE TABLE RELATIONSHIP(
ID NUMBER PRIMARY KEY,
USER_FROM_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_FROM_FK FOREIGN KEY (USER_FROM_ID) REFERENCES USERS(ID),
USER_TO_ID NUMBER NOT NULL,
CONSTRAINT RELATIONSHIP_USER_TO_FK FOREIGN KEY (USER_TO_ID) REFERENCES USERS(ID),
STATUS NVARCHAR2(20), CHECK(STATUS IN('NEW','WAITING_FOR_ACCEPT','FRIENDS','NOT_FRIENDS'))
);

CREATE SEQUENCE RELATIONSHIP_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;