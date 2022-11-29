package kg.kundoluk.school.dto.school;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchoolCourseBulkRequest {
    private Long schoolId;
    private List<Long> courseList;
}
