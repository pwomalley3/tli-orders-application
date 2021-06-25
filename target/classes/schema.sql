CREATE TABLE order_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT NOT NULL,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT NOT NULL,
    UNIQUE KEY order_statuses_name (`name`)
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status_id INT NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT NOT NULL,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT NOT NULL,
    FOREIGN KEY (status_id) REFERENCES order_statuses(id)
);

CREATE TABLE order_line_items (
    `number` INT NOT NULL,
    order_id INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    quantity DECIMAL(10, 2) NOT NULL DEFAULT 1.00,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INT NOT NULL,
    modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    UNIQUE KEY order_line_item_id (`number`, order_id)
);