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



