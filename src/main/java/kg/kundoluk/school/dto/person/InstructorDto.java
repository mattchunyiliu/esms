package kg.kundoluk.school.dto.person;

import kg.kundoluk.school.dto.school.SchoolCourseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class InstructorDto implements Serializable {
    private Long instructorId;
    private Long instructorUserId;
    private String instructorUsername;
    private String instructorTitle;
    private String instructorPhone;
    private List<SchoolCourseDto> instructorCourseTitleDtoList;

    public InstructorDto(Long instructorId, String instructorTitle, String instructorPhone, Long userId, String username) {
        this.instructorId = instructorId;
        this.instructorTitle = instructorTitle;
        this.instructorPhone = instructorPhone;
        this.instructorUserId = userId;
        this.instructorUsername = username;
    }
}
