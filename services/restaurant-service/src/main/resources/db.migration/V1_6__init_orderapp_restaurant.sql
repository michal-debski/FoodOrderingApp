CREATE TABLE RESTAURANT
(
    restaurant_id        SERIAL          NOT NULL,
    NAME                 VARCHAR(64)   NOT NULL,
    EMAIL                VARCHAR(64)   NOT NULL,
    PHONE                VARCHAR(64)   NOT NULL,
    ADDRESS              VARCHAR(100)  NOT NULL,
    PRIMARY KEY (RESTAURANT_ID)

);
