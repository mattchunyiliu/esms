CREATE TABLE IF NOT EXISTS person
(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    first_name VARCHAR(255) not null,
    last_name VARCHAR(255) not null,
    middle_name VARCHAR(255),
    phone_number VARCHAR(255),
    avatar VARCHAR(255),
    gender SMALLINT,
    birthday DATE,
    job TEXT,
    archived BOOLEAN DEFAULT FALSE,
    user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS m2m_instructor_course(
    instructor_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE CASCADE ,
    course_id BIGINT REFERENCES school_course (id) ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE school_class
    ADD COLUMN IF NOT EXISTS person_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE NO ACTION ;

CREATE TABLE IF NOT EXISTS user_school(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
    school_id BIGINT REFERENCES school (id) ON UPDATE CASCADE ON DELETE CASCADE,
    active BOOLEAN DEFAULT TRUE,
    start_year INTEGER,
    end_year INTEGER
);