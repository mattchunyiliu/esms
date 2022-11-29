package kg.kundoluk.school.dto.grade;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GradeMobileCreateDayRequest {
    private Long personId;
    private Long topicId;
    private Long shiftTimeId;
    private String gradeDate;
    private List<GradeMobileStudentDto> studentList;
}
