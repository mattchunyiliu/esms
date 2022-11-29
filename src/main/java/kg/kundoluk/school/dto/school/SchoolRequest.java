package kg.kundoluk.school.dto.school;

import kg.kundoluk.school.model.enums.GradeType;
import kg.kundoluk.school.model.enums.SchoolLanguageType;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.model.location.Region;
import kg.kundoluk.school.model.enums.SchoolType;
import kg.kundoluk.school.model.location.Town;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolRequest {
    String name;
    String code;
    String address;
    String phoneNumber;
    String email;
    Integer studyDay;
    SchoolType schoolType;
    SchoolOrganizationType schoolOrganizationType;
    SchoolLanguageType schoolLanguageType;
    GradeType gradeType;
    Rayon rayon;
    Region region;
    Town town;
    Boolean isTest;
}
