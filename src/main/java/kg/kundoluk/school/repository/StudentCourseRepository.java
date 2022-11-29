package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.model.student.StudentCourse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass"})
    List<StudentCourse> findAllByStudentIdAndChronicleYearId(Long studentId, Long chronicleId);

    @EntityGraph(attributePaths = {"person","schoolCourse","schoolCourse.course","schoolClass"})
    List<StudentCourse> findAllBySchoolClassIdAndChronicleYearId(Long classId, Long chronicleId);

    @Query(value = "select sc.id as studentCourseId, concat(s.last_name,' ',s.first_name) as studentTitle, s.id as studentId, s.subscription_type as subscriptionType from student_course sc left join student s on s.id = sc.student_id where sc.person_id = ?1 and sc.class_id = ?2 and sc.class_id = s.class_id and sc.course_id = ?3 and sc.chronicle_id = ?4 and s.archived = false order by s.last_name, s.first_name", nativeQuery = true)
    List<StudentCourseViewDTO> findAllByPersonClassCourseChronicleYear(Long personId, Long classId, Long courseId, Long chronicleId);

    @EntityGraph(attributePaths = {"student","schoolCourse.course"})
    StudentCourse findFirstById(Long id);

    List<StudentCourse> findAllByPersonIdAndSchoolClassIdAndSchoolCourseIdAndChronicleYearId(Long personId, Long classId, Long courseId, Long chronicleId);

    @Query(value = "select sc.course_id as schoolCourseId, c.id as courseId, c.title as courseTitle, c.title_ru as courseTitleRU, c.title_kg as courseTitleKG,\n" +
            "       concat(p.last_name,' ',p.first_name) as instructorTitle, p.id as instructorId\n" +
            "from student_course sc\n" +
            "    left join person p on sc.person_id = p.id\n" +
            "    left join school_course s on sc.course_id = s.id\n" +
            "    left join course c on s.course_id = c.id\n" +
            "where sc.class_id=?1 and sc.chronicle_id=?2 group by c.id, p.id, sc.course_id", nativeQuery = true)
    List<ClassInstructorCourseDTO> getClassCourseList(Long classId, Long chronicleId);
}
