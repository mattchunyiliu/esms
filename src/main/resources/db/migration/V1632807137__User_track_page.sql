CREATE TABLE IF NOT EXISTS user_track_pages (
        id           BIGSERIAL PRIMARY KEY,
        created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
        updated_at   TIMESTAMP WITHOUT TIME ZONE,
        user_id      BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
        ip_address   VARCHAR(255),
        device       SMALLINT,
        page         VARCHAR(255)
);