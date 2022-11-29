package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.CardAttendanceResource;
import kg.kundoluk.school.controller.web.student.CardAttendanceController;
import kg.kundoluk.school.model.student.CardAttendance;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.stereotype.Component;

@Component
public class CardAttendanceResourceAssembler extends DataTableResourceAssembler<CardAttendance, CardAttendanceResource>{
    public CardAttendanceResourceAssembler() {
        super(CardAttendanceController.class, CardAttendanceResource.class);
    }

    @Override
    public CardAttendanceResource toModel(CardAttendance entity) {
        CardAttendanceResource resource = createModelWithId(entity.getId(), entity);
        resource.setStudentId(entity.getStudent().getId());
        resource.setStudentTitle(entity.getStudent().getSelectorTitle());
        resource.setCreatedAt(TimeHelper.DATE_TIME_FORMATTER.format(entity.getCreatedAt()));
        resource.setAttendanceDate(TimeHelper.DATE_TIME_FORMATTER.format(entity.getAttendanceDate()));
        resource.setType(entity.getAttendanceType());
        return resource;
    }
}
