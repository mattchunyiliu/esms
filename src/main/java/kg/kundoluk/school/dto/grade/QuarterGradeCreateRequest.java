package kg.kundoluk.school.dto.grade;

import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QuarterGradeCreateRequest implements Serializable {
    private Long personId;
    private Long studentCourseId;
    private Long classId;
    private Long quarterId;
    private QuarterGradeMarkType gradeMarkType;
    private String mark;
}
