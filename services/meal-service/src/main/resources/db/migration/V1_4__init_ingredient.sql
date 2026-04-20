
CREATE TABLE IF NOT EXISTS meal_service.ingredients
(
    ingredient_id VARCHAR(255)    NOT NULL,
    meal_id       VARCHAR(255)       NOT NULL,
    name          VARCHAR(255) NOT NULL,
    quantity      INTEGER,
    unit          VARCHAR(32),
    PRIMARY KEY (ingredient_id),
    CONSTRAINT fk_meal
        FOREIGN KEY(meal_id)
        REFERENCES meal_service.meal(meal_id)
        ON DELETE CASCADE
    );