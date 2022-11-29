package kg.kundoluk.school.dto.shiftTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftTimeCreateRequest {
    String startTime;
    String endTime;
    Long shiftId;
    String shiftTitle;
    String title;
    Integer shiftType;
    Long id;
}
