ALTER TABLE student_move_statistics
    ADD COLUMN IF NOT EXISTS initial_count SMALLINT,
    ADD COLUMN IF NOT EXISTS final_count SMALLINT;