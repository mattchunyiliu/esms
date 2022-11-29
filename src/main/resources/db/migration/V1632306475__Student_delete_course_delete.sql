alter table student_course
    drop constraint student_course_student_id_fkey,
    add constraint student_course_student_id_fkey
        foreign key (student_id)
            references student(id)
            on delete cascade;