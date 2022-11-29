/*** Country ***/
CREATE TABLE IF NOT EXISTS ref_location_country
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    title      VARCHAR(255),
    UNIQUE (title)
);

/*** Region ***/
CREATE TABLE IF NOT EXISTS ref_location_region
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    country_id BIGINT NOT NULL REFERENCES ref_location_country (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    title      VARCHAR(255),
    UNIQUE (title)
);

/*** Rayon ***/
CREATE TABLE IF NOT EXISTS ref_location_rayon
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    country_id BIGINT NOT NULL REFERENCES ref_location_country (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    region_id  BIGINT NOT NULL REFERENCES ref_location_region (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    rayon_type smallint,
    title      VARCHAR(255),
    UNIQUE (title)
);

/*** School ***/
CREATE TABLE IF NOT EXISTS school
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    school_name  varchar(255) not null,
    school_code  varchar(100),
    avatar       varchar(255),
    phone_number varchar(13),
    email        varchar(100),
    region_id    BIGINT       NOT NULL REFERENCES ref_location_region (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    rayon_id     BIGINT       NOT NULL REFERENCES ref_location_rayon (id) ON UPDATE CASCADE ON DELETE NO ACTION,
    school_type  smallint,
    grade_type   SMALLINT DEFAULT 0,
    address      varchar(255) not null,
    lon          varchar(255),
    lat          varchar(255),
    study_day    smallint
);

/*** Chronicle Year ***/
CREATE TABLE IF NOT EXISTS chronicle_year
(
    id         BIGSERIAL PRIMARY KEY,
    start_year INTEGER,
    end_year   INTEGER,
    active     BOOLEAN DEFAULT TRUE
);

create unique index on chronicle_year (active)
    where active = true;

/*** Language ***/
CREATE TABLE IF NOT EXISTS language
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(13),
    code varchar(255)
);

INSERT INTO language (name, code)
SELECT 'Кыргызча',
       'kg'
WHERE NOT EXISTS(
        SELECT *
        FROM language
        WHERE code = 'kg');

INSERT INTO language (name, code)
SELECT 'Русский',
       'ru'
WHERE NOT EXISTS(
        SELECT *
        FROM language
        WHERE code = 'ru');


INSERT INTO language (name, code)
SELECT 'English',
       'en'
WHERE NOT EXISTS(
        SELECT *
        FROM language
        WHERE code = 'en');

