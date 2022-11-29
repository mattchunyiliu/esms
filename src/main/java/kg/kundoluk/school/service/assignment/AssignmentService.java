package kg.kundoluk.school.service.assignment;

import kg.kundoluk.school.dto.assignment.AssignmentRequest;
import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentService {
    Assignment create(AssignmentRequest assignmentRequest);
    Assignment edit(AssignmentRequest assignmentRequest, Assignment assignment);
    Assignment findById(Long id);
    void delete(Long id);
    Page<Assignment> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId, Pageable pageable);
    Page<Assignment> findAllByClass(Long classId, Long chronicleId, Pageable pageable);
}
