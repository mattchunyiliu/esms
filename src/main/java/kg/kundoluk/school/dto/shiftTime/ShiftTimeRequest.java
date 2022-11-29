package kg.kundoluk.school.dto.shiftTime;

import kg.kundoluk.school.model.school.Shift;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class ShiftTimeRequest {
    String title;
    Shift shift;
    Integer shiftType;
    LocalTime startAt;
    LocalTime endAt;
}
