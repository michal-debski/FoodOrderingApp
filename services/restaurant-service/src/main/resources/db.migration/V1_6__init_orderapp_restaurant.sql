CREATE TABLE RESTAURANT IF NOT EXISTS
(
    restaurant_id        VARCHAR(255)  NOT NULL,
    NAME                 VARCHAR(64)   NOT NULL,
    EMAIL                VARCHAR(64)   NOT NULL,
    PHONE                VARCHAR(64)   NOT NULL,
    ADDRESS              VARCHAR(100)  NOT NULL,
    OWNER_EMAIL          VARCHAR(255)  NOT NULL,
    PRIMARY KEY (RESTAURANT_ID)

);
