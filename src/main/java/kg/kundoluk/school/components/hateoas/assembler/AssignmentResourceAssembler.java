package kg.kundoluk.school.components.hateoas.assembler;

import kg.kundoluk.school.components.hateoas.resource.AssignmentResource;
import kg.kundoluk.school.controller.web.calendar.AssignmentController;
import kg.kundoluk.school.dto.assignment.AssignmentAttachmentResponse;
import kg.kundoluk.school.model.instructor.Assignment;
import kg.kundoluk.school.model.instructor.AssignmentAttachment;
import kg.kundoluk.school.utils.TimeHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentResourceAssembler extends DataTableResourceAssembler<Assignment, AssignmentResource>{
    public AssignmentResourceAssembler() {
        super(AssignmentController.class, AssignmentResource.class);
    }

    @Override
    public AssignmentResource toModel(Assignment entity) {

        AssignmentResource resource = createModelWithId(entity.getId(), entity);
        resource.setId(entity.getId());
        resource.setCalendarTopicTitle(entity.getCalendarTopic().getTitle());
        resource.setCalendarTopicId(entity.getCalendarTopic().getId());
        resource.setContent(entity.getContent());
        resource.setClassTitle(entity.getCalendarTopic().getCalendarPlan().getSchoolClass().getSelectorTitle());
        resource.setCourseTitle(entity.getCalendarTopic().getCalendarPlan().getSchoolCourse().getCourse().getTitle());
        resource.setDeadlineAt(TimeHelper.DATE_REVERSE_FORMATTER.format(entity.getDeadlineAt()));

        if (entity.getAttachments()!=null){
            List<AssignmentAttachmentResponse> assignmentAttachmentResponses = new ArrayList<>();
            for (AssignmentAttachment assignmentAttachment: entity.getAttachments()) {
                AssignmentAttachmentResponse assignmentAttachmentResponse = new AssignmentAttachmentResponse();
                assignmentAttachmentResponse.setId(assignmentAttachment.getId());
                assignmentAttachmentResponse.setOriginalTitle(assignmentAttachment.getOriginalTitle());
                assignmentAttachmentResponse.setFileUrl(assignmentAttachment.getFileUrl());
                assignmentAttachmentResponses.add(assignmentAttachmentResponse);
            }
            resource.setAttachments(assignmentAttachmentResponses);
        }

        return resource;
    }
}
