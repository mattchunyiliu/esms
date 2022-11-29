package kg.kundoluk.school.dto.notification;

import kg.kundoluk.school.dto.grade.GradeMobileStudentDto;
import kg.kundoluk.school.model.enums.GradeMarkType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class GradePushRequest implements Serializable {
    @NotNull
    private Long studentCourseId;
    private GradeMarkType gradeMarkType;
    private String mark;
    private Long userId;

    public GradePushRequest() {
    }

    public GradePushRequest(GradeMobileStudentDto gradeMobileStudentDto, Long userId) {
        this.studentCourseId = gradeMobileStudentDto.getStudentCourseId();
        this.gradeMarkType = gradeMobileStudentDto.getGradeMarkType();
        this.mark = gradeMobileStudentDto.getMark();
        this.userId = userId;
    }
}
