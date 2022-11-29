CREATE TABLE IF NOT EXISTS user_chat_message (
                                            id BIGSERIAL PRIMARY KEY,
                                            created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
                                            updated_at TIMESTAMP WITHOUT TIME ZONE,
                                            sender_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
                                            recipient_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
                                            ip VARCHAR(255),
                                            attachment_url VARCHAR(255),
                                            chat_room VARCHAR(255),
                                            contents TEXT,
                                            message_type smallint,
                                            status smallint
);

CREATE TABLE IF NOT EXISTS user_chat_room (
                                                 uuid VARCHAR(255) PRIMARY KEY,
                                                 user_id_one BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE ,
                                                 user_id_two BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
