package kg.kundoluk.school.dto.shift;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftCreateRequest {
    String title;
    Long schoolId;
}
