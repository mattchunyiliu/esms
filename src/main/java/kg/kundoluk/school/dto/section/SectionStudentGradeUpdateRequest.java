package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionStudentGradeUpdateRequest {
    Long sectionStudentGradeId;
    String gradeDate;
    String topic;
    String mark;
}
