create table if not exists school_preset (
     id                  BIGSERIAL PRIMARY KEY,
     created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
     updated_at          TIMESTAMP WITHOUT TIME ZONE,
     school_id           bigint references school(id) ON UPDATE CASCADE ON DELETE CASCADE,
     chronicle_id        bigint references chronicle_year(id)  ON UPDATE CASCADE ON DELETE CASCADE,
     is_preset           boolean default false,
     step_number         smallint
);