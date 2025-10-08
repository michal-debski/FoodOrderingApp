CREATE TABLE IF NOT EXISTS restaurant_service.restaurant_street
(
    restaurant_id VARCHAR(255) NOT NULL,
    street_id     SERIAL NOT NULL,
    PRIMARY KEY (restaurant_id, street_id),
    CONSTRAINT fk_restaurant FOREIGN KEY (restaurant_id)
    REFERENCES restaurant_service.restaurant(restaurant_id),
    CONSTRAINT fk_street FOREIGN KEY (street_id)
    REFERENCES restaurant_service.street(street_id)
    );