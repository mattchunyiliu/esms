package kg.kundoluk.school.dto.school;

import com.sun.istack.NotNull;
import kg.kundoluk.school.model.enums.GradeType;
import kg.kundoluk.school.model.enums.SchoolLanguageType;
import kg.kundoluk.school.model.enums.SchoolOrganizationType;
import kg.kundoluk.school.model.enums.SchoolType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;



@Getter
@Setter
public class SchoolCreateRequest {
    String name;
    String code;
    String address;
    String phoneNumber;
    String email;
    Integer studyDay;
    SchoolType schoolType;
    SchoolOrganizationType schoolOrganizationType;
    SchoolLanguageType schoolLanguageType;
    Long rayonId;
    Long regionId;
    Long townId;
    GradeType gradeType;
    Boolean isTest;
}
