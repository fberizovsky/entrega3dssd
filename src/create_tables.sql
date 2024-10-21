CREATE DATABASE dssd;

USE dssd;

CREATE TABLE deposito_principal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE deposito_comunal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE orden (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    principal_deposit_id BIGINT,
    estado VARCHAR(255),
    comunal_deposit_id BIGINT,
    FOREIGN KEY (principal_deposit_id) REFERENCES deposito_principal(id),
    FOREIGN KEY (comunal_deposit_id) REFERENCES deposito_comunal(id)
);

CREATE TABLE item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orden_id BIGINT,
    nombre VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (orden_id) REFERENCES orden(id)
);

CREATE TABLE material_deposito_comunal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    material VARCHAR(255),
    deposito_comunal_id BIGINT,
    deposito_principal_id BIGINT,
    FOREIGN KEY (deposito_comunal_id) REFERENCES deposito_comunal(id),
    FOREIGN KEY (deposito_principal_id) REFERENCES deposito_principal(id)
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);


