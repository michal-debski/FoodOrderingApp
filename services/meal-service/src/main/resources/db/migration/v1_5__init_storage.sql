CREATE TABLE IF NOT EXISTS storage (
                    ingredient_id serial PRIMARY KEY,
                    ingredient_name VARCHAR(255) UNIQUE,
                    quantity INTEGER NOT NULL,
                    unit VARCHAR(255),
                    restaurant_id VARCHAR(255)
);