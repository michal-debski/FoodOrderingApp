CREATE TABLE order_item (
    order_item_id SERIAL NOT NULL,
    ORDER_NUMBER VARCHAR(15) NOT NULL,
    meal_id INT NOT NULL,
    unit_price NUMERIC (5,2) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_item_id),
    CONSTRAINT FK_ORDER_ITEM_ORDER
        FOREIGN KEY (order_number)
            REFERENCES NEW_ORDER (order_number)
);