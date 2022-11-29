package kg.kundoluk.school.dto.quarter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuarterDto {
    private Long schoolId;
    private Long chronicleId;
    private List<QuarterShortResponse> quarters;
}
