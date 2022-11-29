package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionStudentGradeCreateRequest {
    Long sectionStudentId;
    String gradeDate;
    String topic;
    String mark;
}
