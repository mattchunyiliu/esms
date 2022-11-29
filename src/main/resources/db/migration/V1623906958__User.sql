/*** Role ***/
CREATE TABLE IF NOT EXISTS roles (
                                     id         BIGSERIAL PRIMARY KEY,
                                     created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
                                     updated_at TIMESTAMP WITHOUT TIME ZONE,
                                     title VARCHAR(255) UNIQUE,
                                     code VARCHAR(255) UNIQUE
);

/*** User ***/
CREATE TABLE IF NOT EXISTS users (
                                     id         BIGSERIAL PRIMARY KEY,
                                     created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
                                     updated_at TIMESTAMP WITHOUT TIME ZONE,
                                     username VARCHAR(255) UNIQUE,
                                     password VARCHAR(255),
                                     first_name VARCHAR(255) not null,
                                     last_name VARCHAR(255) not null,
                                     middle_name VARCHAR(255),
                                     email VARCHAR(255),
                                     phone_number VARCHAR(255),
                                     phone_verified BOOLEAN DEFAULT FALSE,
                                     avatar VARCHAR(255),
                                     language_id BIGINT REFERENCES language (id) ,
                                     enabled BOOLEAN DEFAULT TRUE
);

/*** User - Role ***/
CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id BIGINT REFERENCES users (id),
                                          role_id BIGINT REFERENCES roles (id)
);


create extension if not exists pgcrypto;

/* All Roles */
INSERT INTO roles (created_at, title, code)
VALUES (now(), 'SUPER ADMIN', 'ROLE_SUPER_ADMIN');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'SCHOOL ADMIN', 'ROLE_ADMIN');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'STUDENT ROLE', 'ROLE_STUDENT');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'INSTRUCTOR ROLE', 'ROLE_INSTRUCTOR');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'PARENT ROLE', 'ROLE_PARENT');

/* Admin User */
INSERT INTO users (created_at, username, password, first_name,last_name,  email, enabled)
values (
           now(),
           'admin',
           crypt('123456', gen_salt('bf', 8)),
           'Admin',
           'Kundoluk',
           'admin@kundoluk.kg',
           true);


/* Admin User and Role*/
INSERT INTO user_roles
(user_id, role_id)
SELECT
    u.id, r.id
FROM
    users u, roles r
WHERE u.username='admin' AND r.code='ROLE_SUPER_ADMIN';