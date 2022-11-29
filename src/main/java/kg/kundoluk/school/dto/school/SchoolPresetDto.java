package kg.kundoluk.school.dto.school;

import kg.kundoluk.school.dto.projection.SchoolPresetDTO;
import lombok.Data;

@Data
public class SchoolPresetDto {
    private Long schoolId;
    private Long chronicleId;
    private Boolean isPreset;
    private Boolean isClassRaised;
    private Integer stepNumber;

    public SchoolPresetDto() {
    }

    public SchoolPresetDto(SchoolPresetDTO schoolPresetDTO, Long chronicleId, Long schoolId) {
        this.schoolId = schoolId;
        this.chronicleId = chronicleId;
        this.stepNumber = schoolPresetDTO.getStepNumber();
        this.isPreset = schoolPresetDTO.getPreset();
    }
}
