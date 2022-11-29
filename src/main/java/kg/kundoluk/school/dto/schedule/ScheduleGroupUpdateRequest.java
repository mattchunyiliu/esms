package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleGroupUpdateRequest {
    private String title;
    private Long schoolId;
    private List<Long> studentList;
}
