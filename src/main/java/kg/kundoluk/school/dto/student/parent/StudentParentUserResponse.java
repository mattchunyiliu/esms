package kg.kundoluk.school.dto.student.parent;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentParentUserResponse extends PersonAbstractDto {
    private Long id;
    private Long studentParentId;
    private Long userId;
    private String username;
    private Long studentId;
    private String studentTitle;
    private String avatar;
    private Integer parentalType;
    private String job;
    private String jobPlace;
}
