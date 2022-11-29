package kg.kundoluk.school.dto.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentClassUpdateRequest {
    private Long classId;
    private Boolean archived = false;
    private List<Long> studentList;
}
