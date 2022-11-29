package kg.kundoluk.school.dto.projection;

public interface QuarterGradeGroupMarkDTO {
    Integer getFives();
    Integer getFivesMale();
    Integer getFours();
    Integer getFoursMale();
    Integer getThrees();
    Integer getThreesMale();
    Integer getTwos();
    Integer getTwosMale();
    Integer getUngraded();
    Long getClassId();
    String getClassTitle();
}
