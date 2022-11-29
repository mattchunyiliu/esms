package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleClassLoadCreateRequest {
    private Long classId;
    private Long personId;
    private Long courseId;
    private Integer hourLoad;
}
