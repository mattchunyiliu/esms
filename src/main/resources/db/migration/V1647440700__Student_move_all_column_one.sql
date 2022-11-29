ALTER TABLE student_move_statistics
    ADD COLUMN IF NOT EXISTS dep_country_count SMALLINT,
    ADD COLUMN IF NOT EXISTS dep_region_count SMALLINT,
    ADD COLUMN IF NOT EXISTS dep_rayon_count SMALLINT,
    ADD COLUMN IF NOT EXISTS arr_country_count SMALLINT,
    ADD COLUMN IF NOT EXISTS arr_region_count SMALLINT,
    ADD COLUMN IF NOT EXISTS arr_rayon_count SMALLINT;

ALTER TABLE student_move_statistics
    DROP COLUMN IF EXISTS move_type,
    DROP COLUMN IF EXISTS action_type;