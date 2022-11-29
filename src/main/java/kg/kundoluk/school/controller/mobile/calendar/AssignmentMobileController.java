package kg.kundoluk.school.controller.mobile.calendar;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kg.kundoluk.school.components.annotations.ApiPageable;
import kg.kundoluk.school.components.hateoas.assembler.AssignmentResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.AssignmentResource;
import kg.kundoluk.school.endpoint.AssignmentEndpoint;
import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("api/mobile/v1/assignment")
public class AssignmentMobileController {
    private final AssignmentEndpoint assignmentEndpoint;

    public AssignmentMobileController(AssignmentEndpoint assignmentEndpoint) {
        this.assignmentEndpoint = assignmentEndpoint;
    }

    @GetMapping("/instructor/{instructor_id}/class/{class_id}/course/{course_id}/chronicle/{chronicle_id}")
    @ApiPageable
    public PagedModel<AssignmentResource> getInstructorAssignments(
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<Assignment> pagedAssembler,
            @PathVariable("instructor_id") Long instructorId,
            @PathVariable("class_id") Long classId,
            @PathVariable("course_id") Long courseId,
            @PathVariable("chronicle_id") Long chronicleId
    ) {
        return pagedAssembler.toModel(
                assignmentEndpoint.findAllByInstructorClassCourse(instructorId, classId, courseId, chronicleId, pageable),
                new AssignmentResourceAssembler()
        );
    }

    @GetMapping("/class/{class_id}/chronicle/{chronicle_id}")
    @ApiPageable
    public PagedModel<AssignmentResource> getClassAssignments(
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<Assignment> pagedAssembler,
            @PathVariable("class_id") Long classId,
            @PathVariable("chronicle_id") Long chronicleId
    ) {
        return pagedAssembler.toModel(
                assignmentEndpoint.findAllByClass(classId, chronicleId, pageable),
                new AssignmentResourceAssembler()
        );
    }

}
