CREATE TABLE IF NOT EXISTS notification (
            id BIGSERIAL PRIMARY KEY,
            created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
            updated_at TIMESTAMP WITHOUT TIME ZONE,
            recipient_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
            sender_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
            notification_type smallint,
            title VARCHAR(255),
            contents TEXT,
            url VARCHAR(255),
            notification_status smallint default 0
);