CREATE TABLE IF NOT EXISTS assignment(
              id BIGSERIAL PRIMARY KEY,
              created_at TIMESTAMP WITHOUT TIME ZONE,
              updated_at TIMESTAMP WITHOUT TIME ZONE,
              topic_id BIGINT REFERENCES calendar_topic (id) ON UPDATE CASCADE ON DELETE CASCADE ,
              title VARCHAR(255),
              content TEXT
);

CREATE TABLE IF NOT EXISTS assignment_attachments(
         id BIGSERIAL PRIMARY KEY,
         assignment_id BIGINT REFERENCES assignment (id) ON UPDATE CASCADE ON DELETE CASCADE ,
         file_url VARCHAR(255),
         original_title VARCHAR(255)
);
