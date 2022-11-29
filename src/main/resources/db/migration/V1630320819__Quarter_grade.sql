ALTER TABLE school ADD COLUMN IF NOT EXISTS is_test BOOLEAN DEFAULT false;

CREATE TABLE IF NOT EXISTS quarter_grade(
                                    id BIGSERIAL PRIMARY KEY,
                                    created_at TIMESTAMP WITHOUT TIME ZONE,
                                    updated_at TIMESTAMP WITHOUT TIME ZONE,
                                    instructor_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
                                    m2m_student_course_id BIGINT REFERENCES student_course (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
                                    quarter_id BIGINT REFERENCES quarter (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
                                    class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE NO ACTION ,
                                    grade_type SMALLINT,
                                    mark varchar(10),
                                    comment varchar(255)
);

ALTER SEQUENCE if exists quarter_grade_id_seq INCREMENT BY 10;