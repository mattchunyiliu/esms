ALTER TABLE course ADD COLUMN IF NOT EXISTS title_tr VARCHAR(255);

ALTER TABLE school
    ADD COLUMN IF NOT EXISTS organization_type SMALLINT DEFAULT 0,
    ADD COLUMN IF NOT EXISTS language_type SMALLINT DEFAULT 0;