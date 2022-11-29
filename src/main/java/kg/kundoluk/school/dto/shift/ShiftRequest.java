package kg.kundoluk.school.dto.shift;

import kg.kundoluk.school.model.school.School;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShiftRequest {
    String title;
    School school;
}
