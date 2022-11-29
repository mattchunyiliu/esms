package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.CalendarTopicResource;
import kg.kundoluk.school.controller.mobile.calendar.CalendarTopicController;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import org.springframework.stereotype.Component;

@Component
public class CalendarTopicResourceAssembler extends DataTableResourceAssembler<CalendarTopic, CalendarTopicResource>{
    public CalendarTopicResourceAssembler() {
        super(CalendarTopicController.class, CalendarTopicResource.class);
    }

    @Override
    public CalendarTopicResource toModel(CalendarTopic entity) {
        CalendarTopicResource resource = createModelWithId(entity.getId(), entity);
        resource.setCalendarPlanId(entity.getCalendarPlan().getId());
        resource.setDatePlan(entity.getDatePlan().toString());
        resource.setHours(entity.getHours());
        resource.setId(entity.getId());
        resource.setQuarterId(entity.getQuarter().getId());
        resource.setTitle(entity.getTitle());
        resource.setTopicResult(entity.getTopicResult());
        resource.setTopicVisibility(entity.getTopicVisibility());
        resource.setIsPassed(entity.getIsPassed());

        return resource;
    }
}
