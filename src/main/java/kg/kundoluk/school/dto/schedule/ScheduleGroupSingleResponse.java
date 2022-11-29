package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleGroupSingleResponse {
    private String title;
    private List<Long> studentList;
}
