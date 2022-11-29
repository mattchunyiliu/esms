package kg.kundoluk.school.dto.assignment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class AssignmentCreateRequest implements Serializable {
    @NotNull
    private Long calendarTopicId;
    private String title;
    private String content;
    @NotNull
    private String deadlineAt;
}
