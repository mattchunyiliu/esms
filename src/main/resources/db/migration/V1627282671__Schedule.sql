/*** Schedule ***/
CREATE TABLE IF NOT EXISTS schedule (
         id         BIGSERIAL PRIMARY KEY,
         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
         updated_at TIMESTAMP WITHOUT TIME ZONE,
         week_day SMALLINT,
         class_id   bigint references school_class(id) ON UPDATE CASCADE ON DELETE CASCADE,
         shift_time_id   bigint references shift_time(id) ON UPDATE CASCADE ON DELETE CASCADE,
         person_id   bigint references person(id) ON UPDATE CASCADE ON DELETE CASCADE,
         course_id   bigint references school_course(id) ON UPDATE CASCADE ON DELETE CASCADE,
         is_group  BOOLEAN DEFAULT FALSE,
         group_title VARCHAR(255)
);