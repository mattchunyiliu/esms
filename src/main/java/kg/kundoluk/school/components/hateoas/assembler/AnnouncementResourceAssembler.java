package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.AnnouncementResource;
import kg.kundoluk.school.controller.web.announcement.AnnouncementController;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.model.announcement.Announcement;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class AnnouncementResourceAssembler  extends DataTableResourceAssembler<Announcement, AnnouncementResource>{
    public AnnouncementResourceAssembler() {
        super(AnnouncementController.class, AnnouncementResource.class);
    }

    @Override
    public AnnouncementResource toModel(Announcement entity) {
        AnnouncementResource resource = createModelWithId(entity.getId(), entity);
        resource.setId(entity.getId());
        resource.setTitle(entity.getTitle());
        resource.setDescription(entity.getDescription());
        resource.setCreatedBy(entity.getUser().getSelectorTitle());
        resource.setCreatedAt(TimeHelper.DATE_TIME_FORMATTER.format(entity.getCreatedAt()));
        if (entity.getClasses()!=null){
            resource.setClasses(entity.getClasses().stream().map(sc-> sc.getSchoolClass().getSelectorTitle()).collect(Collectors.toList()));
        }
        resource.setRoles(entity.getRoles().stream().map(Role::getCode).collect(Collectors.toList()));
        return resource;
    }
}
