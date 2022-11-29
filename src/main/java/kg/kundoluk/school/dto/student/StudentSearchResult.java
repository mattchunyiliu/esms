package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.utils.TimeHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentSearchResult  {
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean archived;
    private Integer gender;
    private String dateOfBirth;
    private String phone;
    private String classTitle;
    private String schoolTitle;
    private String instructorTitle;
    private List<PersonAbstractDto> parents;

    public StudentSearchResult(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.middleName = student.getMiddleName();
        this.gender = student.getGender().ordinal();
        this.archived = student.getArchived();
        if (student.getBirthday()!=null)
            this.dateOfBirth = TimeHelper.DATE_REVERSE_FORMATTER.format(student.getBirthday());
        this.classTitle = student.getSchoolClass().getSelectorTitle();
        if (student.getSchoolClass().getPerson() != null)
            this.instructorTitle = student.getSchoolClass().getPerson().getSelectorTitle();
        this.schoolTitle = student.getSchoolClass().getSchool().getName();
    }
}
