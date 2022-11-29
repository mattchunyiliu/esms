/*** User Register Application Form ***/
CREATE TABLE IF NOT EXISTS user_application_form (
         id         BIGSERIAL PRIMARY KEY,
         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
         updated_at TIMESTAMP WITHOUT TIME ZONE,
         first_name VARCHAR(255) not null,
         last_name VARCHAR(255) not null,
         middle_name VARCHAR(255) ,
         phone_number VARCHAR(255),
         school_id   bigint references school(id) ON UPDATE CASCADE ON DELETE CASCADE,
         role_id   bigint references roles(id) ON UPDATE CASCADE ON DELETE CASCADE,
         class_id   bigint references school_class(id) ON UPDATE CASCADE ON DELETE CASCADE,
         comment  TEXT,
         status SMALLINT
);