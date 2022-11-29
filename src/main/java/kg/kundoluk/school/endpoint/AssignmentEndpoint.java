package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.assignment.AssignmentCreateDefaultRequest;
import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.assignment.AssignmentResponse;
import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AssignmentEndpoint {
    Assignment create(AssignmentCreateRequest assignmentCreateRequest, MultipartFile[] multipartFile) throws IOException;
    Assignment edit(AssignmentCreateRequest assignmentCreateRequest, MultipartFile[] multipartFile, Assignment assignment) throws IOException;
    AssignmentResponse findById(Long id);
    Page<Assignment> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId, Pageable pageable);
    Page<Assignment> findAllByClass(Long classId, Long chronicleId, Pageable pageable);
    void delete(Long id);
    void deleteAttachment(Long id);
    void deleteAttachments(List<Long> ids);
    void addAttachment(Assignment assignment, MultipartFile[] multipartFiles) throws IOException;
    Assignment createTopicLess(AssignmentCreateDefaultRequest assignmentCreateRequest, MultipartFile[] multipartFile) throws IOException;


}
