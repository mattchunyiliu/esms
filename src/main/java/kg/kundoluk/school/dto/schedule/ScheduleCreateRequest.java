package kg.kundoluk.school.dto.schedule;

import kg.kundoluk.school.model.enums.WeekDay;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ScheduleCreateRequest {
    private WeekDay weekDay;
    @NotNull
    private Long classId;
    @NotNull
    private Long shiftTimeId;
    @NotNull
    private Long instructorId;
    @NotNull
    private Long courseId;
    private Boolean isGroup;
    private String groupTitle;
    private Long scheduleGroupId;
    private Long chronicleId;
}
