alter table school_class
drop constraint school_class_school_id_fkey,
add constraint school_class_school_id_fkey
    foreign key (school_id)
        references school(id)
        on delete cascade;

alter table school_course
    drop constraint school_course_school_id_fkey,
    add constraint school_course_school_id_fkey
        foreign key (school_id)
            references school(id)
            on delete cascade;

alter table person
    drop constraint person_user_id_fkey,
    add constraint person_user_id_fkey
        foreign key (user_id)
            references users(id)
            on delete cascade;

alter table student
    drop constraint student_user_id_fkey,
    add constraint student_user_id_fkey
        foreign key (user_id)
            references users(id)
            on delete cascade;

alter table student
    drop constraint student_class_id_fkey,
    add constraint student_class_id_fkey
        foreign key (class_id)
            references school_class(id)
            on delete cascade;