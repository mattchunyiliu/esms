package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.GradeMarkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeMobileResponse {
    private Long gradeId;
    private String gradeDate;
    private Long studentCourseId;
    private Long shiftTimeId;
    private Long topicId;
    private GradeMarkType gradeMarkType;
    private String mark;
}
