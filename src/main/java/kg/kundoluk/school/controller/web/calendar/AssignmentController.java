package kg.kundoluk.school.controller.web.calendar;


import kg.kundoluk.school.components.annotations.ApiPageable;
import kg.kundoluk.school.components.hateoas.assembler.AssignmentResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.AssignmentResource;
import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.assignment.AssignmentCreateDefaultRequest;
import kg.kundoluk.school.dto.assignment.AssignmentCreateRequest;
import kg.kundoluk.school.dto.assignment.AssignmentResponse;
import kg.kundoluk.school.endpoint.AssignmentEndpoint;
import kg.kundoluk.school.model.instructor.Assignment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/web/v1/assignment")
public class AssignmentController {
    private final AssignmentEndpoint assignmentEndpoint;

    public AssignmentController(AssignmentEndpoint assignmentEndpoint) {
        this.assignmentEndpoint = assignmentEndpoint;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> create(
            @ModelAttribute AssignmentCreateRequest assignmentCreateRequest,
            @RequestParam(name = "files", required = false) MultipartFile[] files
    ) throws IOException {
        assignmentEndpoint.create(assignmentCreateRequest, files);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @RequestMapping(value = "/topicless/create", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> createTopicless(
            @ModelAttribute AssignmentCreateDefaultRequest assignmentCreateRequest,
            @RequestParam(name = "files", required = false) MultipartFile[] files
    ) throws IOException {
        assignmentEndpoint.createTopicLess(assignmentCreateRequest, files);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> edit(
            @ModelAttribute AssignmentCreateRequest assignmentCreateRequest,
            @RequestParam(name = "files", required = false) MultipartFile[] files,
            @PathVariable("id") Assignment assignment
    ) throws IOException {
        assignmentEndpoint.edit(assignmentCreateRequest, files, assignment);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public AssignmentResponse get(@PathVariable("id") Long id) {
        return assignmentEndpoint.findById(id);
    }

    @DeleteMapping
    public Boolean delete(
            @RequestParam("id") Long id
    ) {
        assignmentEndpoint.delete(id);
        return true;
    }

    @RequestMapping(value = "/assignment/{id}/file", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addFile(
            @RequestParam(name = "files", required = false) MultipartFile[] files,
            @PathVariable("id") Assignment assignment
    ) throws IOException {
        assignmentEndpoint.addAttachment(assignment, files);
        return new ResponseEntity<>(new ApiResponse(true, "UPDATED"), HttpStatus.OK);
    }

    @DeleteMapping({"/file/{file_id:[\\d]+}"})
    public ResponseEntity<?> deleteFile(
            @PathVariable Long file_id
    ) {
        assignmentEndpoint.deleteAttachment(file_id);
        return new ResponseEntity<>(new ApiResponse(true, "Attachment has been deleted"), HttpStatus.OK);
    }

    @DeleteMapping({"/files"})
    public ResponseEntity<?> deleteFiles(
            @RequestBody List<Long> ids
    ) {
        assignmentEndpoint.deleteAttachments(ids);
        return new ResponseEntity<>(new ApiResponse(true, "Attachment has been deleted"), HttpStatus.OK);
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
}
