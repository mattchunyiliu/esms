/*** School Class ***/
CREATE TABLE IF NOT EXISTS school_class
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    level      SMALLINT,
    label      VARCHAR (255),
    class_type SMALLINT,
    school_id  BIGINT NOT NULL REFERENCES school (id),
    shift_id  BIGINT REFERENCES shift (id),
    language_id  BIGINT REFERENCES language (id)
);