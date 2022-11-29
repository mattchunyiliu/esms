package kg.kundoluk.school.dto.projection;

public interface CalendarTopicDTO {
    Long getId();
    String getTitle();
    Long getQuarterId();
    String getTopicDate();
    Integer getTopicHour();
    String getTopicResult();
    String getTopicVisibility();
    Boolean getIsPassed();
}
