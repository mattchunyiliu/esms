package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ScheduleGroupResponse implements Serializable {
    private Long classId;
    private String classTitle;
    private Long groupId;
    private String groupTitle;
    private Integer studentCount;
}
