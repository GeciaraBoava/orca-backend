CREATE SEQUENCE customer_seq START 1 INCREMENT 1;

CREATE TABLE customer (
    id BIGINT PRIMARY KEY DEFAULT nextval('customer_seq'),
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    contact_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL,
    customer_type VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE SEQUENCE material_seq START 1 INCREMENT 1;

CREATE TABLE material (
    id BIGINT PRIMARY KEY DEFAULT nextval('material_seq'),
    description VARCHAR(255) NOT NULL,
    materrial_type VARCHAR(20),
    unit_measure VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    current_price DECIMAL(12,4) NOT NULL
);


CREATE SEQUENCE material_type_seq START 1 INCREMENT 1;

CREATE TABLE material_type (
    id BIGINT PRIMARY KEY DEFAULT nextval('material_type_seq'),
    description VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE SEQUENCE price_history_seq START 1 INCREMENT 1;

CREATE TABLE price_history (
    id BIGINT PRIMARY KEY DEFAULT nextval('price_history_seq'),
    material_id BIGINT NOT NULL,
    price DECIMAL(12,4) NOT NULL,
    description VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE SEQUENCE supplier_seq START 1 INCREMENT 1;

CREATE TABLE supplier (
    id BIGINT PRIMARY KEY DEFAULT nextval('supplier_seq'),
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    contact_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE SEQUENCE unit_measure_seq START 1 INCREMENT 1;

CREATE TABLE unit_measure (
    id BIGINT PRIMARY KEY DEFAULT nextval('unit_measure_seq'),
    description VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);


CREATE SEQUENCE users_seq START 1 INCREMENT 1;

CREATE TABLE users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
    login VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    is_active BOOLEAN NOT NULL,
    user_role VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

