package kg.kundoluk.school.service.assignment;

import kg.kundoluk.school.dto.assignment.AssignmentAttachmentRequest;
import kg.kundoluk.school.model.instructor.AssignmentAttachment;

import java.util.List;

public interface AssignmentAttachmentService {
    AssignmentAttachment create(AssignmentAttachmentRequest assignmentAttachmentRequest);
    void delete(Long id);
    void deleteList(List<Long> ids);
}
