package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.GradeClassGroupAverage;
import kg.kundoluk.school.dto.projection.QuarterGradeGroupMarkDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentAnalyticDTO;
import kg.kundoluk.school.dto.projection.QuarterGradeStudentGroupMarkDTO;
import kg.kundoluk.school.model.grade.QuarterGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuarterGradeAnalyticRepository extends JpaRepository<QuarterGrade, Long> {

    @Query(value = "select concat(s.last_name, ' ', s.first_name) as studentTitle, s.id as studentId, s.gender as studentGender, sc.person_id as instructorId,\n" +
            "       c.id as courseId,\n" +
            "       c.title as courseTitle,\n" +
            "       c.title_ru as courseTitleRU,\n" +
            "       c.title_kg as courseTitleKG,\n" +
            "       qg.quarter_id as quarterId, qg.grade_type as gradeType,\n" +
            "       qg.mark as quarterMark\n" +
            "from student s\n" +
            "    left join student_course sc on s.id = sc.student_id\n" +
            "    left join school_course sc2 on sc.course_id = sc2.id\n" +
            "    left join course c on sc2.course_id = c.id\n" +
            "    left join quarter_grade qg on sc.id = qg.m2m_student_course_id\n" +
            "where s.class_id = ?1 and s.archived=false and sc.archived=false and (qg.quarter_id is null or ?2 = 0 or qg.quarter_id = ?2) and (qg.grade_type is null or ?3 = -1 or  qg.grade_type = ?3) and sc.chronicle_id=?4 and (?5 = 0 or sc2.id = ?5)\n" +
            "order by s.last_name, c.title_ru", nativeQuery = true)
    List<QuarterGradeStudentAnalyticDTO> getAllStudentAnalytic(Long classId, Long quarterId, Integer gradeType, Long chronicleId, Long courseId);

    @Query(value = "select count(CASE WHEN d.totalFive!=0 and d.totalFour=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as fives," +
            "count(CASE WHEN d.gender = 1 and d.totalFive!=0 and d.totalFour=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as fivesMale,\n" +
            "       count(CASE WHEN d.gender = 1 and (d.totalFive!=0 or d.totalFive=0) and d.totalFour!=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as foursMale," +
            "count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and d.totalFour!=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as fours,\n" +
            "       count(CASE WHEN d.gender = 1 and (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0) and d.totalTwo=0 THEN 1 END) as threesMale," +
            "count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0) and d.totalTwo=0 THEN 1 END) as threes,\n" +
            "       count(CASE WHEN d.gender = 1 and (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0 or d.totalThree=0) and d.totalTwo!=0 THEN 1 END) as twosMale," +
            "count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0 or d.totalThree=0) and d.totalTwo!=0 THEN 1 END) as twos,\n" +
            "       count(CASE WHEN d.totalFive=0 and d.totalFour=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as ungraded, d.classId, d.classTitle \n" +
            "from (select\n" +
            "                      count(CASE WHEN qg.mark = '5' THEN 1 END) as totalFive,\n" +
            "                      count(CASE WHEN qg.mark = '4' THEN 1 END) as totalFour,\n" +
            "                      count(CASE WHEN qg.mark = '3' THEN 1 END) as totalThree,\n" +
            "                      count(CASE WHEN qg.mark = '2' THEN 1 END) as totalTwo, s.gender as gender, " +
            "scl.id as classId, concat(scl.level,'',scl.label) as classTitle\n" +
            "               from student s\n" +
            "                        left join student_course m2msc on m2msc.student_id = s.id\n" +
            "                        left join quarter_grade qg on qg.m2m_student_course_id=m2msc.id\n" +
            "                        left join school_class scl on scl.id = s.class_id\n" +
            "               where scl.school_id=?1 and (qg.quarter_id is null or qg.quarter_id=?3) and (qg.grade_type is null or qg.grade_type=0) and s.archived=false and (?2=0 or scl.id = ?2)\n" +
            "               group by s.id, scl.id) as d group by d.classId, d.classTitle", nativeQuery = true)
    List<QuarterGradeGroupMarkDTO> getStudentGroupByMark(Long schoolId, Long classId, Long quarterId);

    @Query(value = "select count(CASE WHEN d.totalFive!=0 and d.totalFour=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as fives,\n" +
            "       count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and d.totalFour!=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as fours,\n" +
            "       count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0) and d.totalTwo=0 THEN 1 END) as threes,\n" +
            "       count(CASE WHEN (d.totalFive!=0 or d.totalFive=0) and (d.totalFour!=0 or d.totalFour=0) and (d.totalThree!=0 or d.totalThree=0) and d.totalTwo!=0 THEN 1 END) as twos,\n" +
            "       count(CASE WHEN d.totalFive=0 and d.totalFour=0 and d.totalThree=0 and d.totalTwo=0 THEN 1 END) as ungraded,\n" +
            "       d.studentTitle as studentTitle, d.gender as studentGender\n" +
            "from (select\n" +
            "          count(CASE WHEN qg.mark = '5' THEN 1 END) as totalFive,\n" +
            "          count(CASE WHEN qg.mark = '4' THEN 1 END) as totalFour,\n" +
            "          count(CASE WHEN qg.mark = '3' THEN 1 END) as totalThree,\n" +
            "          count(CASE WHEN qg.mark = '2' THEN 1 END) as totalTwo,\n" +
            "          s.gender,\n" +
            "             concat(s.last_name,' ',s.first_name) as studentTitle\n" +
            "      from student s\n" +
            "               left join student_course m2msc on m2msc.student_id = s.id\n" +
            "               left join quarter_grade qg on qg.m2m_student_course_id=m2msc.id\n" +
            "               left join school_class scl on scl.id = s.class_id\n" +
            "      where (?1=0 or scl.school_id=?1) and (qg.quarter_id is null or qg.quarter_id=?3) and (qg.grade_type is null or qg.grade_type=0) and s.archived=false and (?2=0 or scl.id = ?2)\n" +
            "      group by s.id) as d GROUP BY d.studentTitle, d.gender", nativeQuery = true)
    List<QuarterGradeStudentGroupMarkDTO> getStudentsGroupByMark(Long schoolId, Long classId, Long quarterId);

    @Query(value = "select scl.id as classId, concat(scl.level,'',scl.label) as classTitle, AVG(CAST(qg.mark AS INTEGER )) as averageGrade\n" +
            "from quarter_grade qg\n" +
            "         left join student_course m2msc on qg.m2m_student_course_id = m2msc.id\n" +
            "         left join school_class scl on m2msc.class_id = scl.id\n" +
            "where scl.school_id=?1 and qg.quarter_id=?2 and qg.grade_type=0\n" +
            "group by scl.id;", nativeQuery = true)
    List<GradeClassGroupAverage> getClassAverageGrade(Long schoolId, Long quarterId);
}
