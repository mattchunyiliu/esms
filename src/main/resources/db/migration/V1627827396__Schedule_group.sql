CREATE TABLE IF NOT EXISTS schedule_group(
                id BIGSERIAL PRIMARY KEY,
                created_at TIMESTAMP WITHOUT TIME ZONE,
                updated_at TIMESTAMP WITHOUT TIME ZONE,
                class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE CASCADE ,
                title varchar(255)
);

CREATE TABLE IF NOT EXISTS m2m_schedule_group_student(
        schedule_group_id BIGINT REFERENCES schedule_group (id) ON UPDATE CASCADE ON DELETE CASCADE ,
        student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE
);
