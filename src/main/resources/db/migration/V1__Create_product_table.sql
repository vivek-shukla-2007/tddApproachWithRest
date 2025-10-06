CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    productName VARCHAR(255) NOT NULL,
    productPrice DECIMAL(19, 4) NOT NULL,
    --category VARCHAR(255),
    PRIMARY KEY (id)
);