package kg.kundoluk.school.dto.quarter;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.model.school.School;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class QuarterRequest {
    String title;
    LocalDate startDate;
    LocalDate endDate;
    ChronicleYear chronicleYear;
    School school;
    Boolean status;
}
