CREATE TABLE IF NOT EXISTS card_attendance(
             id BIGSERIAL PRIMARY KEY,
             created_at TIMESTAMP WITHOUT TIME ZONE,
             updated_at TIMESTAMP WITHOUT TIME ZONE,
             student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE ,
             attendance_type SMALLINT,
             attendance_date TIMESTAMP WITHOUT TIME ZONE
);
