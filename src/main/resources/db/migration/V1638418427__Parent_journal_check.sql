CREATE TABLE IF NOT EXISTS grade_parent_check(
       id BIGSERIAL PRIMARY KEY,
       created_at TIMESTAMP WITHOUT TIME ZONE,
       updated_at TIMESTAMP WITHOUT TIME ZONE,
       student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE ,
       user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
       start_date DATE,
       end_date DATE,
       is_checked BOOLEAN DEFAULT FALSE,
       week_number SMALLINT
);