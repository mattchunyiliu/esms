package kg.kundoluk.school.dto.person;

import kg.kundoluk.school.dto.school.SchoolBaseDto;
import kg.kundoluk.school.model.Person;
import kg.kundoluk.school.model.user.User;
import kg.kundoluk.school.utils.TimeHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PersonSearchResult {
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer gender;
    private String dateOfBirth;
    private String phone;
    private List<SchoolBaseDto> schools;

    public PersonSearchResult(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.middleName = person.getMiddleName();
        if (person.getBirthday()!=null)
            this.dateOfBirth = TimeHelper.DATE_REVERSE_FORMATTER.format(person.getBirthday());
        this.phone = person.getPhone();
        this.schools = person.getUser().getSchools().stream().map(us-> new SchoolBaseDto(us.getId(), us.getName(), us.getAddress())).collect(Collectors.toList());
    }
}
