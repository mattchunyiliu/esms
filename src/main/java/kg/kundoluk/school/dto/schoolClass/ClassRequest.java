package kg.kundoluk.school.dto.schoolClass;

import kg.kundoluk.school.model.references.Language;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.school.Shift;
import kg.kundoluk.school.model.enums.ClassType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassRequest {
    String label;
    Integer level;
    ClassType classType;
    School school;
    Shift shift;
    Language language;
    Person person;
}
