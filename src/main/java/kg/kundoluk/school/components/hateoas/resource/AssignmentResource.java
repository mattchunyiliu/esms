package kg.kundoluk.school.components.hateoas.resource;


import kg.kundoluk.school.dto.assignment.AssignmentAttachmentResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
public class AssignmentResource extends RepresentationModel<AssignmentResource> {
    private Long id;
    private String content;
    private String calendarTopicTitle;
    private Long calendarTopicId;
    private String classTitle;
    private String courseTitle;
    private String deadlineAt;
    private List<AssignmentAttachmentResponse> attachments;
}
