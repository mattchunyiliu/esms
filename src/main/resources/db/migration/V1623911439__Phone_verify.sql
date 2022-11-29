CREATE TABLE IF NOT EXISTS phone_verification (
                                                  id         BIGSERIAL PRIMARY KEY,
                                                  created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
                                                  updated_at TIMESTAMP WITHOUT TIME ZONE,
                                                  phone_number VARCHAR(255) not null,
                                                  verification_code VARCHAR(255) not null,
                                                  expire_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);