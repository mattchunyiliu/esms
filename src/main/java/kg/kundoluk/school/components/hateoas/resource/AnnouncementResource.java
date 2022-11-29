package kg.kundoluk.school.components.hateoas.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
public class AnnouncementResource extends RepresentationModel<AnnouncementResource> {
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private String createdBy;
    private List<String> roles;
    private List<String> classes;
}
