package kg.kundoluk.school.dto.student.parent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeParentCheckCreateRequest {
    Long studentId;
    Long userId;
    String startDate;
    String endDate;
    Boolean check;
    Integer weekNumber;
}
