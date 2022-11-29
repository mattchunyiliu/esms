package kg.kundoluk.school.dto.projection;

public interface ScheduleInstructorDTO {
    Long getId();
    Integer getWeekDay();
    Long getShiftTimeId();
    String getShiftTimeTitle();
}
