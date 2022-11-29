CREATE TABLE IF NOT EXISTS section(
        id BIGSERIAL PRIMARY KEY,
        created_at TIMESTAMP WITHOUT TIME ZONE,
        updated_at TIMESTAMP WITHOUT TIME ZONE,
        school_id BIGINT REFERENCES school (id) ON UPDATE CASCADE ON DELETE CASCADE ,
        title varchar(255),
        description text
);

CREATE TABLE IF NOT EXISTS section_instructor(
       id BIGSERIAL PRIMARY KEY,
       created_at TIMESTAMP WITHOUT TIME ZONE,
       updated_at TIMESTAMP WITHOUT TIME ZONE,
       section_id BIGINT REFERENCES section (id) ON UPDATE CASCADE ON DELETE CASCADE ,
       instructor_id BIGINT REFERENCES person (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS section_schedule(
      id BIGSERIAL PRIMARY KEY,
      created_at TIMESTAMP WITHOUT TIME ZONE,
      updated_at TIMESTAMP WITHOUT TIME ZONE,
      section_instructor_id BIGINT REFERENCES section_instructor (id) ON UPDATE CASCADE ON DELETE CASCADE ,
      week_day SMALLINT,
      start_time varchar(255),
      end_time varchar(255)
);

CREATE TABLE IF NOT EXISTS section_student(
   id BIGSERIAL PRIMARY KEY,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   section_instructor_id BIGINT REFERENCES section_instructor (id) ON UPDATE CASCADE ON DELETE CASCADE ,
   student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE
);

alter table calendar_topic
    ADD COLUMN IF NOT EXISTS is_passed BOOLEAN DEFAULT FALSE;