CREATE TABLE IF NOT EXISTS announcement(
      id BIGSERIAL PRIMARY KEY,
      created_at TIMESTAMP WITHOUT TIME ZONE,
      updated_at TIMESTAMP WITHOUT TIME ZONE,
      school_id BIGINT REFERENCES school (id) ON UPDATE CASCADE ON DELETE CASCADE ,
      user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
      title varchar(255),
      description text
);

CREATE TABLE IF NOT EXISTS announcement_role(
       announcement_id BIGINT REFERENCES announcement (id) ON UPDATE CASCADE ON DELETE CASCADE ,
       role_id BIGINT REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS announcement_attachments(
       id BIGSERIAL PRIMARY KEY,
       announcement_id BIGINT REFERENCES announcement (id) ON UPDATE CASCADE ON DELETE CASCADE ,
       file_url VARCHAR(255),
       original_title VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS announcement_class(
        id BIGSERIAL PRIMARY KEY,
        announcement_id BIGINT REFERENCES announcement (id) ON UPDATE CASCADE ON DELETE CASCADE ,
        class_id BIGINT REFERENCES school_class (id) ON UPDATE CASCADE ON DELETE CASCADE,
        course_id BIGINT REFERENCES school_course (id) ON UPDATE CASCADE ON DELETE CASCADE
);