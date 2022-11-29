package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.NotificationResource;
import kg.kundoluk.school.controller.web.notification.NotificationController;
import kg.kundoluk.school.model.notification.Notification;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.stereotype.Component;

@Component
public class NotificationResourceAssembler extends DataTableResourceAssembler<Notification, NotificationResource>{
    public NotificationResourceAssembler() {
        super(NotificationController.class, NotificationResource.class);
    }

    @Override
    public NotificationResource toModel(Notification entity) {
        NotificationResource resource = createModelWithId(entity.getId(), entity);
        resource.setContent(entity.getContents());
        resource.setCreatedAt(TimeHelper.DATE_TIME_FORMATTER.format(entity.getCreatedAt()));
        resource.setId(entity.getId());
        resource.setNotificationStatusType(entity.getStatus());
        resource.setNotificationType(entity.getNotificationType());
        if (entity.getSender()!=null)
            resource.setSenderTitle(entity.getSender().getSelectorTitle());
        return resource;
    }
}
