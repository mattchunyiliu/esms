CREATE TABLE IF NOT EXISTS section_student_grade(
          id BIGSERIAL PRIMARY KEY,
          created_at TIMESTAMP WITHOUT TIME ZONE,
          updated_at TIMESTAMP WITHOUT TIME ZONE,
          section_student_id BIGINT REFERENCES section_student (id) ON UPDATE CASCADE ON DELETE CASCADE ,
          grade_date DATE,
          mark varchar(10),
          topic varchar(255)
);

ALTER TABLE section_student
    ADD COLUMN IF NOT EXISTS chronicle_id BIGINT REFERENCES chronicle_year (id) ON UPDATE CASCADE ON DELETE CASCADE