package kg.kundoluk.school.dto.assignment;

import kg.kundoluk.school.model.instructor.Assignment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignmentAttachmentRequest {
    Assignment assignment;
    String fileUrl;
    String originalTitle;
}
