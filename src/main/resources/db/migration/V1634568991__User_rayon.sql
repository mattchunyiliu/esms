/*** User - Rayon ***/
CREATE TABLE IF NOT EXISTS user_rayon (
      user_id BIGINT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
      rayon_id BIGINT REFERENCES ref_location_rayon (id) ON UPDATE CASCADE ON DELETE CASCADE
);
