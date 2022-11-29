package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.references.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
