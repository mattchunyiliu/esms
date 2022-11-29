package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleClassLoadUpdateRequest {
    private Long id;
    private Integer hourLoad;
}
