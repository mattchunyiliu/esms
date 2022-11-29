package kg.kundoluk.school.dto.user;

import kg.kundoluk.school.dto.role.RoleShortDto;
import kg.kundoluk.school.dto.school.SchoolBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileDto {
    private UserShortDto user;
    private Long personId;
    private List<RoleShortDto> role;
    private List<SchoolBaseDto> schools;
    private List<Long> rayon;
}
