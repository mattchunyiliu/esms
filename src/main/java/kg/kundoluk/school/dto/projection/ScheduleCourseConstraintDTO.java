package kg.kundoluk.school.dto.projection;

public interface ScheduleCourseConstraintDTO {
    Long getId();
    Long getShiftTimeId();
    String getShiftTimeName();
    Long getCourseId();
    Integer getWeekDay();
}
