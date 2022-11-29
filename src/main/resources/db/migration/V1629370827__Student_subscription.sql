ALTER TABLE student ADD COLUMN IF NOT EXISTS subscription_type SMALLINT DEFAULT 0;

CREATE TABLE IF NOT EXISTS student_payment_history(
  id BIGSERIAL PRIMARY KEY,
  created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE,
  student_id BIGINT REFERENCES student (id) ON UPDATE CASCADE ON DELETE CASCADE,
  chronicle_id BIGINT REFERENCES chronicle_year (id) ON UPDATE CASCADE ON DELETE CASCADE,
  amount numeric(10,2),
  description TEXT
);