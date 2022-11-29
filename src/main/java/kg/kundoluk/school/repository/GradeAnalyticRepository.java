package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.model.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface GradeAnalyticRepository extends JpaRepository<Grade, Long> {

    @Query(value = "select c.title as courseTitle, c.title_kg as courseTitleKG, c.title_ru as courseTitleRU, AVG(CAST(g.mark AS INTEGER )) as averageGrade\n" +
            "from grade g\n" +
            "         left join student_course sc on sc.id = g.m2m_student_course_id\n" +
            "         left join school_course s on sc.course_id = s.id\n" +
            "         left join course c on s.course_id = c.id\n" +
            "where g.grade_type = 0 and sc.student_id = ?1 and g.grade_date between ?2 and ?3\n" +
            "group by c.id", nativeQuery = true)
    List<GradeStudentAnalytic> getAnalyticStudent(Long studentId, LocalDate start, LocalDate end);

    @Query(value = "select concat(s.last_name,' ',s.first_name) as studentTitle, AVG(CAST(g.mark AS INTEGER )) as averageGrade\n" +
            "from student s\n" +
            "          LEFT JOIN student_course sc on sc.student_id = s.id\n" +
            "          LEFT JOIN grade g on sc.id = g.m2m_student_course_id\n" +
            "where (g.grade_type = 0 or g.grade_type is null) and (g.grade_date between ?2 and ?3 or g.grade_date is null) and s.class_id = ?1 and (?4 = 0 or sc.course_id = ?4)\n" +
            "group by s.id order by averageGrade desc", nativeQuery = true)
    List<GradeStudentAnalytic> getAnalyticClass(Long classId, LocalDate start, LocalDate end, Long courseId);

    @Query(value = "select c.id as id,\n" +
            "       concat(c.level,'',c.label) as title,\n" +
            "       AVG(CAST(g.mark AS INTEGER )) as averageGrade,\n" +
            "       TO_CHAR(DATE_TRUNC('month',g.grade_date), 'Month') as monthName\n" +
            "from grade g\n" +
            "    left join student_course sc on sc.id = g.m2m_student_course_id\n" +
            "    left join student s on sc.student_id = s.id\n" +
            "    left join school_class c on s.class_id = c.id\n" +
            "where g.grade_type = 0 and c.school_id = ?1 and g.grade_date between ?2 and ?3\n" +
            "group by c.id, DATE_TRUNC('month',g.grade_date)", nativeQuery = true)
    List<GradeGroupMonthAnalytic> getAnalyticSchoolByMonth(Long schoolId, LocalDate start, LocalDate end);

    @Query(value = "select concat(s.last_name,' ',s.first_name) as studentTitle, c.id as courseId, c.title as courseTitle, c.title_kg as courseTitleKG, c.title_ru as courseTitleRU, AVG(CAST(g.mark AS INTEGER )) as averageGrade\n" +
            "from student s\n" +
            "          LEFT JOIN student_course sc on sc.student_id = s.id\n" +
            "          LEFT JOIN grade g on sc.id = g.m2m_student_course_id\n" +
            "          LEFT JOIN school_course ssc on ssc.id = sc.course_id\n" +
            "          LEFT JOIN course c on ssc.course_id = c.id\n" +
            "where (g.grade_type = 0 or g.grade_type is null) and (g.grade_date between ?2 and ?3 or g.grade_date is null) and s.class_id = ?1\n" +
            "group by s.id, c.id order by averageGrade desc", nativeQuery = true)
    List<GradeStudentAnalytic> getAnalyticClassGroupCourse(Long classId, LocalDate start, LocalDate end);

    @Query(value = "select COUNT(g.id) as attendanceCount\n" +
            "from grade g left join student_course sc on sc.id = g.m2m_student_course_id\n" +
            "where g.grade_type = 1 and g.grade_date between ?2 and ?3 and sc.student_id = ?1", nativeQuery = true)
    Integer getStudentAttendanceCount(Long studentId, LocalDate start, LocalDate end);

    @Query(value = "select p.id as instructorId, concat(p.last_name,' ',p.first_name) as instructorTitle, count(g.id) as totalCount " +
            "from person p\n" +
            "    left join grade g on p.id = g.instructor_id\n" +
            "    left join user_school us on p.user_id = us.user_id\n" +
            "where us.school_id=?1 and (g.created_at is null or g.created_at between ?2 and ?3) and (g.grade_type is null or g.grade_type=0) and p.archived=false group by p.id, p.last_name order by p.last_name", nativeQuery = true)
    List<GradeCountAnalytic> getInstructorGradeCount(Long schoolId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(distinct d.instructor_id) as totalInstructor, TRIM(TO_CHAR(DATE_TRUNC('month',d.gradeDate), 'Month')) as monthName\n" +
            "from (select g.instructor_id, g.grade_date as gradeDate\n" +
            "      from grade g\n" +
            "               left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "               left join school_class scl on sc.class_id = scl.id\n" +
            "      where scl.school_id = ?1 and g.grade_type = 0 and g.mark in ('2','3','4','5') and g.grade_date between ?2 and ?3\n" +
            "      group by g.instructor_id, g.grade_date order by g.grade_date) as d group by DATE_TRUNC('month',d.gradeDate)", nativeQuery = true)
    List<GradeInstructorMonth> getInstructorActivityMonth(Long schoolId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select sch.id as schoolId, sch.school_name as schoolName, sch.organization_type as schoolOrganizationType, count(g.id) as totalCount, AVG(CAST(g.mark AS INTEGER )) as averageGrade\n" +
            "from school sch\n" +
            "    left join user_school us on sch.id = us.school_id\n" +
            "    left join person p on p.user_id = us.user_id\n" +
            "left join grade g on p.id = g.instructor_id\n" +
            "where (?1 = 0 or sch.rayon_id=?1) and (?2 = 0 or sch.town_id=?2) and (?3 = 0 or sch.id = ?3) and (g.created_at is null or g.created_at between ?4 and ?5) and sch.is_test = false and p.archived=false and (g.grade_type is null or g.grade_type=0) and (g.mark is null or g.mark in ('2','3','4','5'))\n" +
            "group by sch.id\n" +
            "order by averageGrade", nativeQuery = true)
    List<GradeSchoolGroupAnalytic> getSchoolGradeCount(Long rayonId, Long townId, Long schoolId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select\n" +
            "    sch.id as schoolId,\n" +
            "    count(distinct sc.student_id) as totalCount\n" +
            "from grade g\n" +
            "         left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "         left join school_class scl on sc.class_id = scl.id\n" +
            "         left join school sch on sch.id = scl.school_id\n" +
            "where sch.rayon_id=?1 and (?2 = 0 or sch.town_id = ?2) and sch.is_test = false and g.grade_type = 1 and g.mark in ('92','95') and g.grade_date between ?3 and ?4\n" +
            "group by sch.id", nativeQuery = true)
    List<GradeSchoolGroupAnalytic> getSchoolAttendanceCount(Long rayonId, Long townId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(case when d.gradeCount!=0 then 1 end) as gradeCount,\n" +
            "       count(case when d.attendanceCount!=0 then 1 end) as attendanceCount,\n" +
            "       d.classId,d.classTitle\n" +
            "from (select concat(scl.level,'',scl.label) as classTitle,\n" +
            "             scl.id as classId,\n" +
            "             count(case when g.grade_type=0 then 1 end) as gradeCount,\n" +
            "             count(case when g.grade_type=1 then 1 end) as attendanceCount\n" +
            "      from person p\n" +
            "               left join grade g on p.id = g.instructor_id\n" +
            "               left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "               left join school_class scl on sc.class_id = scl.id\n" +
            "      where p.id=?1 and g.created_at between ?2 and ?3 group by  scl.id, sc.student_id) as d group by d.classId, d.classTitle", nativeQuery = true)
    List<GradeInstructorClassGroupAnalytic> getInstructorGroupClassGradeCount(Long instructorId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(case when d.reasonableAttendanceCount!=0 then 1 end) count92,\n" +
            "       count(case when d.unreasonableAttendanceCount!=0 and d.reasonableAttendanceCount=0 then 1 end) count95,\n" +
            "\t   d.monthName\n" +
            "from (\n" +
            "\tselect count(case when g.mark='92' then 1 end) as reasonableAttendanceCount,\n" +
            "\t count(case when g.mark='95' then 1 end) as unreasonableAttendanceCount,\n" +
            "\t TO_CHAR(DATE_TRUNC('month',g.grade_date), 'Month') as monthName,\n" +
            "\t sc.student_id as studentId\n" +
            "\t\tfrom grade g\n" +
            "\t\t\t\tleft join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "\t\t\t\tleft join school_class scl on sc.class_id = scl.id\n" +
            "\t   where scl.school_id = ?1 and (?2 =0 or scl.id = ?2) and g.grade_type = 1 and g.mark in ('92','95') and g.grade_date between ?3 and ?4\n" +
            "\t   group by sc.student_id, DATE_TRUNC('month',g.grade_date)\n" +
            ") as d group by d.monthName", nativeQuery = true)
    List<AttendanceReasonCountMonth> getAttendanceGroupReasonMonth(Long schoolId, Long classId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(case when d.reasonableAttendanceCount > 3 then 1 end) as count92,\n" +
            "       count(case when d.unreasonableAttendanceCount > 3 then 1 end) as count95,\n" +
            "       d.classId\n" +
            "from (\n" +
            "         select count(case when g.mark='92' then 1 end) as reasonableAttendanceCount,\n" +
            "                count(case when g.mark='95' then 1 end) as unreasonableAttendanceCount,\n" +
            "                sc.student_id as studentId,\n" +
            "                scl.id as classId\n" +
            "         from grade g\n" +
            "             left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "             left join school_class scl on sc.class_id = scl.id\n" +
            "         where scl.school_id = ?1 and g.grade_type = 1 and g.mark in ('92','95') and g.grade_date between ?2 and ?3\n" +
            "         group by sc.student_id, scl.id, DATE_TRUNC('day',g.grade_date)\n" +
            "     ) as d group by d.classId;", nativeQuery = true)
    List<AttendanceReasonCountClass> getAttendanceGroupReasonQuarter(Long schoolId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(case when d.reasonableAttendanceCount!=0 then 1 end) count92Day,\n" +
            "       count(case when d.unreasonableAttendanceCount!=0 and d.reasonableAttendanceCount=0 then 1 end) count95Day,\n" +
            "       sum(d.reasonableAttendanceCount) count92Lesson,\n" +
            "       sum(d.unreasonableAttendanceCount) count95Lesson,\n" +
            "       d.studentId,\n" +
            "       TO_CHAR(DATE_TRUNC('month',d.attendanceDate), 'Month') as monthName\n" +
            "from (select count(case when g.mark='92' then 1 end) as reasonableAttendanceCount,\n" +
            "             count(case when g.mark='95' then 1 end) as unreasonableAttendanceCount,\n" +
            "             g.grade_date as attendanceDate,\n" +
            "             s.id as studentId\n" +
            "      from student s\n" +
            "               left join student_course sc on s.id = sc.student_id\n" +
            "               left join grade g on sc.id = g.m2m_student_course_id\n" +
            "      where s.class_id = ?1 and g.grade_type = 1 and g.mark in ('92','95') and g.grade_date between ?2 and ?3\n" +
            "      group by sc.student_id, g.grade_date, s.id order by s.id) as d group by DATE_TRUNC('month',d.attendanceDate), d.studentId order by d.studentId", nativeQuery = true)
    List<AttendanceReasonCountStudentMonth> getAttendanceGroupReasonStudentMonth(Long classId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select count(case when g.mark='92' then 1 end) as count92,\n" +
            "       count(case when g.mark='95' then 1 end) as count95,\n" +
            "       g.grade_date as attendanceDate\n" +
            "from grade g\n" +
            "         left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "         left join school_class scl on sc.class_id = scl.id\n" +
            "where scl.school_id = ?1 and g.grade_type = 1 and g.mark in ('92','95') and g.grade_date between ?2 and ?3\n" +
            "group by sc.student_id, g.grade_date order by g.grade_date", nativeQuery = true)
    List<AttendanceReasonCountDay> getAttendanceGroupReasonDay(Long schoolId, LocalDate startDate, LocalDate endDate);

    @Query(value = "select concat(s.last_name,' ',s.first_name) as studentTitle,\n" +
            "       concat(scl.level,'',scl.label) as classTItle,\n" +
            "       g.grade_date as gradeDate,\n" +
            "       g.mark as gradeMark,\n" +
            "       c2.title as courseTitle,\n" +
            "       c2.title_ru as courseTitleRU,\n" +
            "       c2.title_kg as courseTitleKG\n" +
            "from grade g\n" +
            "         left join student_course sc on g.m2m_student_course_id = sc.id\n" +
            "         left join student s on sc.student_id = s.id\n" +
            "         left join school_class scl on s.class_id = scl.id\n" +
            "         left join school_course c on sc.course_id = c.id\n" +
            "         left join course c2 on c.course_id = c2.id\n" +
            "where g.instructor_id=?1 and s.class_id=?2 and g.created_at between ?3 and ?4 and g.grade_type=0 order by g.grade_date desc", nativeQuery = true)
    List<GradeStudentDate> getInstructorClassGradeList(Long instructorId, Long classId, LocalDate startDate, LocalDate endDate);
}
