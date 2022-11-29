package kg.kundoluk.school.dto.quarter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuarterCreateRequest {

    private String quarter;
    private String startDate;
    private String endDate;
    private Long chronicleId;
    private Long schoolId;
    private Boolean status;

    public QuarterCreateRequest() {
    }

    public QuarterCreateRequest(Long chronicleId, Long schoolId, String startDate, String endDate, Boolean status, String quarter) {
        this.chronicleId = chronicleId;
        this.schoolId = schoolId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.quarter = quarter;
    }
}
