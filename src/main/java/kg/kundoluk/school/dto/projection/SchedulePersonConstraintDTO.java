package kg.kundoluk.school.dto.projection;

public interface SchedulePersonConstraintDTO {
    Long getId();
    Long getShiftTimeId();
    String getShiftTimeName();
    Long getPersonId();
    Integer getWeekDay();
}
