package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.model.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShortDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String avatar;

    public UserShortDto() {
    }

    public UserShortDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.avatar = user.getAvatar();
    }
}
