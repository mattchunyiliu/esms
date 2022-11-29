package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonCourseBulkRequest {
    private Long personId;
    private List<Long> courseList;
}
