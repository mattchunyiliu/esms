package kg.kundoluk.school.dto.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionStudentGradeResponse {
    private Long gradeId;
    private Long sectionStudentId;
    private String studentTitle;
    private String mark;
    private String gradeDate;
}
