CREATE TABLE IF NOT EXISTS course
(
    id           BIGSERIAL PRIMARY KEY,
    title        varchar(255),
    title_kg     varchar(255),
    title_ru     varchar(255),
    color_hex    varchar(255)
);

CREATE TABLE IF NOT EXISTS school_course
(
    id            BIGSERIAL PRIMARY KEY,
    school_id     BIGINT REFERENCES school (id),
    course_id     BIGINT REFERENCES course (id),
    archived      BOOLEAN DEFAULT FALSE
);