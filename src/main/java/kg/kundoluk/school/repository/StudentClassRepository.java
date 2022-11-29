package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.student.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {

    Boolean existsByChronicleYearAndSchoolClassAndStudent(ChronicleYear chronicleYear, SchoolClass schoolClass, Student student);

    List<StudentClass> findAllByStudentId(Long studentId);
}
