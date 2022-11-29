CREATE TABLE IF NOT EXISTS student_move_statistics (
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    quarter_id   BIGINT REFERENCES quarter (id) ON UPDATE CASCADE ON DELETE CASCADE,
    class_id     BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE CASCADE,
    action_type  SMALLINT,
    gender       SMALLINT,
    move_type    SMALLINT,
    count        SMALLINT
);