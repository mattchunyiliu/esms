CREATE TABLE IF NOT EXISTS schedule_class_load(
     id BIGSERIAL PRIMARY KEY,
     class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE CASCADE ,
     person_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE CASCADE ,
     course_id BIGINT REFERENCES school_course (id) ON UPDATE CASCADE ON DELETE CASCADE ,
     hour_load SMALLINT
);

CREATE TABLE IF NOT EXISTS schedule_person_constraint(
      id BIGSERIAL PRIMARY KEY,
      person_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE CASCADE ,
      shift_time_id BIGINT REFERENCES shift_time (id) ON UPDATE CASCADE ON DELETE CASCADE ,
      week_day SMALLINT,
      is_allowed BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS schedule_course_constraint(
     id BIGSERIAL PRIMARY KEY,
     course_id BIGINT REFERENCES school_course (id) ON UPDATE CASCADE ON DELETE CASCADE ,
     shift_time_id BIGINT REFERENCES shift_time (id) ON UPDATE CASCADE ON DELETE CASCADE ,
     week_day SMALLINT,
     is_allowed BOOLEAN DEFAULT FALSE
);

ALTER SEQUENCE if exists schedule_class_load_id_seq INCREMENT BY 10;