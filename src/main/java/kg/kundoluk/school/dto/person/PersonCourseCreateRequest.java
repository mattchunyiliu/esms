package kg.kundoluk.school.dto.person;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PersonCourseCreateRequest {
    @NotNull
    private Long personId;
    @NotNull
    private Long courseId;
}
