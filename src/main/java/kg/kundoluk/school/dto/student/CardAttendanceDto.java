package kg.kundoluk.school.dto.student;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CardAttendanceDto implements Serializable {
    private Long studentId;
    private Integer attendanceType;
}
