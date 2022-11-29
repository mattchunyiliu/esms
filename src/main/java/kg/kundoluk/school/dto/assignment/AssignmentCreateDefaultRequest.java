package kg.kundoluk.school.dto.assignment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AssignmentCreateDefaultRequest {
    @NotNull
    private Long instructorId;
    private Long classId;
    private Long courseId;
    private Long quarterId;
    private Long chronicleId;
    private String title;
    private String content;
    @NotNull
    private String deadlineAt;
}
