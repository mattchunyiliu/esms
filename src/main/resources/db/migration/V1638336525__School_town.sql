ALTER TABLE school
    ADD COLUMN IF NOT EXISTS town_id  BIGINT REFERENCES ref_location_town (id) ON UPDATE CASCADE ON DELETE NO ACTION;