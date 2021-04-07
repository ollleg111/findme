/*
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateRead;
    private User userFrom;
    private User userTo;
 */

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

ALTER TABLE MESSAGE ADD DATE_EDITED TIMESTAMP;
ALTER TABLE MESSAGE ADD DATE_DELETED TIMESTAMP;