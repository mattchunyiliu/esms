CREATE TABLE IF NOT EXISTS user_login_history (
        id           BIGSERIAL PRIMARY KEY,
        created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
        updated_at   TIMESTAMP WITHOUT TIME ZONE,
        user_id      BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
        action_type  SMALLINT
);