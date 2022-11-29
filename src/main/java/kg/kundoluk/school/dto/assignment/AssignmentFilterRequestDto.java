package kg.kundoluk.school.dto.assignment;

import kg.kundoluk.school.components.page.PageRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentFilterRequestDto {
    private PageRequestDto pageRequest;
    private AssignmentSearchRequest searchRequest;
}
