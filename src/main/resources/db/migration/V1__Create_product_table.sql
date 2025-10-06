CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    product_price DECIMAL(19, 4) NOT NULL,
    --category VARCHAR(255),
    PRIMARY KEY (id)
);