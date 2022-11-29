package kg.kundoluk.school.dto.announcement;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AnnouncementCreateRequest {
    @NotNull
    private Long schoolId;
    @NotNull
    private Long userId;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private List<Long> roles;
    private List<AnnouncementClassDto> classes;
}
