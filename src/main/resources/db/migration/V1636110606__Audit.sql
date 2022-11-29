CREATE TABLE IF NOT EXISTS revision (
                                        id          BIGSERIAL PRIMARY KEY,
                                        "timestamp" BIGINT,
                                        auditor_id  BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS grade_aud AS TABLE grade;
ALTER TABLE grade_aud
    ADD COLUMN IF NOT EXISTS revtype SMALLINT,
    ADD COLUMN IF NOT EXISTS rev BIGINT REFERENCES revision (id) ON UPDATE NO ACTION ON DELETE CASCADE ;


CREATE TABLE IF NOT EXISTS quarter_grade_aud AS TABLE quarter_grade;
ALTER TABLE quarter_grade_aud
    ADD COLUMN IF NOT EXISTS revtype SMALLINT,
    ADD COLUMN IF NOT EXISTS rev BIGINT REFERENCES revision (id) ON UPDATE NO ACTION ON DELETE CASCADE ;