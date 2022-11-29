package kg.kundoluk.school.dto.report;

import kg.kundoluk.school.model.enums.Gender;
import kg.kundoluk.school.model.enums.MoveActionType;
import kg.kundoluk.school.model.enums.MoveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MoveReportDto {
    @NotNull
    Long quarterId;
    @NotNull
    Long classId;
    MoveType moveType;
    MoveActionType moveActionType;
    Gender gender;
    Integer count;
    Integer initialCount;
    Integer finalCount;
    Integer depCountryCount;
    Integer depRegionCount;
    Integer depRayonCount;
    Integer arrCountryCount;
    Integer arrRegionCount;
    Integer arrRayonCount;
}
