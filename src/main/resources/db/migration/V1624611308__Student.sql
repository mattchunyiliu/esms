CREATE TABLE IF NOT EXISTS student
(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    first_name VARCHAR(255) not null,
    last_name VARCHAR(255) not null,
    middle_name VARCHAR(255),
    avatar VARCHAR(255),
    gender SMALLINT,
    birthday DATE,
    archived BOOLEAN DEFAULT FALSE,
    user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS student_course
(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    student_id BIGINT REFERENCES student (id),
    class_id BIGINT REFERENCES school_class (id),
    course_id BIGINT REFERENCES school_course (id),
    person_id BIGINT REFERENCES person (id),
    chronicle_id BIGINT REFERENCES chronicle_year (id),
    archived BOOLEAN DEFAULT FALSE,
    UNIQUE (student_id, class_id, course_id, person_id, chronicle_id)
);

CREATE TABLE IF NOT EXISTS student_class
(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE CASCADE,
    chronicle_id BIGINT REFERENCES chronicle_year (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (student_id, class_id, chronicle_id)
);

CREATE TABLE IF NOT EXISTS student_parent
(
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
    parent_id BIGINT REFERENCES person(id) ON UPDATE CASCADE ON DELETE CASCADE,
    parental_type SMALLINT
);