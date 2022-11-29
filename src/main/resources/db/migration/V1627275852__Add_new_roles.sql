INSERT INTO roles (created_at, title, code)
VALUES (now(), 'SCHOOL DIRECTOR', 'ROLE_SCHOOL_DIRECTOR');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'SCHOOL HEADER', 'ROLE_SCHOOL_HEADER');

INSERT INTO roles (created_at, title, code)
VALUES (now(), 'RAYON HEADER', 'ROLE_RAYON_HEADER');

ALTER TABLE student
    ADD COLUMN IF NOT EXISTS nationality VARCHAR(255);

ALTER TABLE person
    ADD COLUMN IF NOT EXISTS job_place VARCHAR(255);