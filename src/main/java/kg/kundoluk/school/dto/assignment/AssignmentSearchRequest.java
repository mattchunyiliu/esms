package kg.kundoluk.school.dto.assignment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentSearchRequest {
    private Long instructorId;
    private Long chronicleId;
}
