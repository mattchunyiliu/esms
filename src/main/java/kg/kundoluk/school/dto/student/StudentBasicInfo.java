package kg.kundoluk.school.dto.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentBasicInfo {
    private Long id;
    private Long studentId;
    private String studentTitle;
    private String classTitle;
    private String avatar;
}
