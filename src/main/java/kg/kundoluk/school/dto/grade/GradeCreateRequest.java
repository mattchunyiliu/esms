package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.GradeMarkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeCreateRequest {
    private Long personId;
    private Long studentCourseId;
    private Long topicId;
    private Long shiftTimeId;
    private String gradeDate;
    private GradeMarkType gradeMarkType;
    private String mark;
}
