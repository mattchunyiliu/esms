package kg.kundoluk.school.dto.student;

import kg.kundoluk.school.dto.person.PersonAbstractDto;
import kg.kundoluk.school.model.enums.SubscriptionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUserResponse extends PersonAbstractDto {
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private String nationality;
    private String classTitle;
    private Long classId;
    private Long schoolId;
    private String schoolTitle;
    private SubscriptionType subscriptionType;
}
