package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.GradeMarkType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class GradeMobileStudentDto implements Serializable {
    @NotNull
    private Long studentCourseId;
    private Integer subscriptionType;
    private GradeMarkType gradeMarkType;
    private String mark;
}
