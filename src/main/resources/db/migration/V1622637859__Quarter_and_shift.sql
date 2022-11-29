/*** Quarter ***/
CREATE TABLE IF NOT EXISTS quarter
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    start_at   DATE,
    end_at     DATE,
    title      VARCHAR (255),
    active     BOOLEAN DEFAULT FALSE,
    chronicle_id  BIGINT NOT NULL REFERENCES chronicle_year (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    school_id  BIGINT NOT NULL REFERENCES school (id) ON UPDATE CASCADE ON DELETE CASCADE
);

/*** Shift ***/
CREATE TABLE IF NOT EXISTS shift
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    title      VARCHAR (255),
    school_id  BIGINT NOT NULL REFERENCES school (id) ON UPDATE CASCADE ON DELETE CASCADE
);

/*** Shift Time ***/
CREATE TABLE IF NOT EXISTS shift_time
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    title      VARCHAR (255),
    start_at   TIME,
    end_at     TIME,
    shift_id   BIGINT NOT NULL REFERENCES shift (id) ON UPDATE CASCADE ON DELETE CASCADE
);