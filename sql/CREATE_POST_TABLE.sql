/*
    private Long id;
    private String message;
    private Date datePosted;
    private User userPosted;
 */

CREATE TABLE POST(
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(100),
DATE_POSTED TIMESTAMP,
USER_ID NUMBER NOT NULL,
CONSTRAINT USER_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);

CREATE SEQUENCE POST_SEQ INCREMENT BY 1 MAXVALUE 1000 CYCLE;

DROP TABLE POST;
/*
private Long id;
private String message;
private Date datePosted;
private String location;
private Set<User> usersTagged = new HashSet<>();
private User userPosted;
private User userPagePosted;
 */

CREATE TABLE POST(
ID NUMBER PRIMARY KEY,
MESSAGE NVARCHAR2(200),
DATE_POSTED TIMESTAMP,
LOCATION NVARCHAR2(50),

);