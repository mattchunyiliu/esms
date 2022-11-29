package kg.kundoluk.school.dto.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceCountDto {
    Integer attendanceCount;
    Integer dayCount;
}
