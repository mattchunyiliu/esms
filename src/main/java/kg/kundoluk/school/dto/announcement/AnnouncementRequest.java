package kg.kundoluk.school.dto.announcement;


import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.school.School;
import kg.kundoluk.school.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class AnnouncementRequest {
    School school;
    User user;
    String title;
    String description;
    Set<Role> roles;
}
