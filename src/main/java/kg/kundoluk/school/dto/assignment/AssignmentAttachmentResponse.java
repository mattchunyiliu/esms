package kg.kundoluk.school.dto.assignment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentAttachmentResponse {
    private Long id;
    private String originalTitle;
    private String fileUrl;
}
