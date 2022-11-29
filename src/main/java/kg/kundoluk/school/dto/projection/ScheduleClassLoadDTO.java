package kg.kundoluk.school.dto.projection;

import lombok.Getter;
import lombok.Setter;


public interface ScheduleClassLoadDTO {
    Long getId();
    Long getCourseId();
    Long getClassId();
    Long getPersonId();
    Integer getHours();
}
