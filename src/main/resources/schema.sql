DROP TABLE IF EXISTS PERSON;
CREATE TABLE IF NOT EXISTS PERSON (
   id BIGINT AUTO_INCREMENT NOT NULL,
   full_name VARCHAR(255),
   title VARCHAR(255),
   age INT NOT NULL
);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE IF NOT EXISTS BOOK
(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID BIGINT,
    TITLE VARCHAR (255),
    AUTHOR VARCHAR (255),
    PAGE_COUNT INTEGER
);
