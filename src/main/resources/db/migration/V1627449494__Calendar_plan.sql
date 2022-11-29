create table if not exists calendar_plan (
         id                  BIGSERIAL PRIMARY KEY,
         created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
         updated_at          TIMESTAMP WITHOUT TIME ZONE,
         title               VARCHAR(255),
         person_id           bigint references person(id) ON UPDATE CASCADE ON DELETE CASCADE,
         class_id            bigint references school_class(id) ON UPDATE CASCADE ON DELETE CASCADE,
         course_id           bigint references school_course(id) ON UPDATE CASCADE ON DELETE CASCADE,
         chronicle_id        bigint references chronicle_year(id)  ON UPDATE CASCADE ON DELETE CASCADE
);

create table if not exists calendar_topic (
         id                  BIGSERIAL PRIMARY KEY,
         created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
         updated_at          TIMESTAMP WITHOUT TIME ZONE,
         title               VARCHAR(255),
         hours               INTEGER,
         date_plan           DATE,
         date_fact           DATE,
         quarter_id          bigint references quarter(id) ON UPDATE CASCADE ON DELETE CASCADE,
         calendar_plan_id    bigint references calendar_plan(id) ON UPDATE CASCADE ON DELETE CASCADE,
         topic_result        TEXT,
         topic_visibility    TEXT
);