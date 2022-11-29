package kg.kundoluk.school.dto.assignment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignmentResponse {
    private Long id;
    private String content;
    private Long calendarTopicId;
    private String calendarTopicTitle;
    private String classTitle;
    private String courseTitle;
    private String deadlineAt;
    private List<AssignmentAttachmentResponse> attachments;
}
