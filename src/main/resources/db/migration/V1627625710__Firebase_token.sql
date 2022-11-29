CREATE TABLE IF NOT EXISTS m2m_user_firebase_token(
        id                  BIGSERIAL PRIMARY KEY,
        created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
        updated_at          TIMESTAMP WITHOUT TIME ZONE,
        user_id             BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
        firebase_token      TEXT
);