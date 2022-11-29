package kg.kundoluk.school.dto.course;

import kg.kundoluk.school.model.enums.CourseType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CourseRequest {

    private String title;
    private String titleKg;
    private String titleRu;
    private String titleTr;
    private String colorHex;
    private CourseType courseType = CourseType.GENERAL;
}
