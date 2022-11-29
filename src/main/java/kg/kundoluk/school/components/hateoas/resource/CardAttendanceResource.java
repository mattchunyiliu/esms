package kg.kundoluk.school.components.hateoas.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CardAttendanceResource extends RepresentationModel<CardAttendanceResource> {
    private Long studentId;
    private String studentTitle;
    private Integer type;
    private String createdAt;
    private String attendanceDate;
}
