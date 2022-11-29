package kg.kundoluk.school.dto.student.parent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentParentBulkDto {
    private Long parentId;
    private Integer parentalType;
    private List<StudentParentDto> parentStudentDtoList;
}
