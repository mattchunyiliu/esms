CREATE TABLE IF NOT EXISTS grade(
        id BIGSERIAL PRIMARY KEY,
        created_at TIMESTAMP WITHOUT TIME ZONE,
        updated_at TIMESTAMP WITHOUT TIME ZONE,
        instructor_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
        m2m_student_course_id BIGINT REFERENCES student_course (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
        topic_id BIGINT REFERENCES calendar_topic (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
        shift_time_id BIGINT REFERENCES shift_time (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
        grade_date DATE,
        grade_type SMALLINT,
        mark varchar(10),
        comment varchar(255)
);

ALTER SEQUENCE if exists student_course_id_seq INCREMENT BY 10;
ALTER SEQUENCE if exists grade_id_seq INCREMENT BY 10;