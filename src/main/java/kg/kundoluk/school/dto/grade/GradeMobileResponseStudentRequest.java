package kg.kundoluk.school.dto.grade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeMobileResponseStudentRequest {
    private Long studentId;
    private Long courseId;
    private String startDate;
    private String endDate;
}
