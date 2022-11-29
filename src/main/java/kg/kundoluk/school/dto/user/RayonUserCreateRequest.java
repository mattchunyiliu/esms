package kg.kundoluk.school.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RayonUserCreateRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private Long rayonId;
}
