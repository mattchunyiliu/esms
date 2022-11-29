package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.GradeMarkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeMobileUpdateRequest {
    private Long gradeId;
    private Long studentCourseId;
    private GradeMarkType gradeMarkType;
    private String mark;
}
