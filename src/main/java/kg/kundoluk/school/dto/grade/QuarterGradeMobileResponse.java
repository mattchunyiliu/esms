package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuarterGradeMobileResponse {
    private Long gradeId;
    private Long studentCourseId;
    private Long quarterId;
    private QuarterGradeMarkType gradeMarkType;
    private String mark;
}
