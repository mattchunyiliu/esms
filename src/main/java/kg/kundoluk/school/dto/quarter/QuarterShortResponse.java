package kg.kundoluk.school.dto.quarter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuarterShortResponse {
    String quarter;
    String startDate;
    String endDate;
    Boolean status;
    Long id;
}
