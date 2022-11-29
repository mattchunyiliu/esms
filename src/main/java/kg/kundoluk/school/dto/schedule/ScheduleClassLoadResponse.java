package kg.kundoluk.school.dto.schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleClassLoadResponse {
    private Long id;
    private Long classId;
    private String classTitle;
    private String personTitle;
    private String courseTitle;
    private String courseTitleKG;
    private String courseTitleRU;
    private Integer hourLoad;
}
