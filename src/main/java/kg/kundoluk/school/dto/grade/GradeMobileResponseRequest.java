package kg.kundoluk.school.dto.grade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeMobileResponseRequest {
    private Long personId;
    private Long courseId;
    private Long classId;
    private String date;
    private Long shiftTimeId;
}
