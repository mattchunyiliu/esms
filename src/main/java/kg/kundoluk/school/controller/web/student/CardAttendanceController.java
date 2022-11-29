package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.components.annotations.ApiPageable;
import kg.kundoluk.school.components.hateoas.assembler.CardAttendanceResourceAssembler;
import kg.kundoluk.school.components.hateoas.resource.CardAttendanceResource;
import kg.kundoluk.school.constants.Constants;
import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.endpoint.CardAttendanceEndpoint;
import kg.kundoluk.school.model.student.CardAttendance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("api/v1/card-attendance")
public class CardAttendanceController {
    private final CardAttendanceEndpoint cardAttendanceEndpoint;

    public CardAttendanceController(CardAttendanceEndpoint cardAttendanceEndpoint) {
        this.cardAttendanceEndpoint = cardAttendanceEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("status") Integer status, @RequestParam("student_id") Long studentId)  {
        cardAttendanceEndpoint.create(studentId, status);
        return new ResponseEntity<>(new ApiResponse(true, Constants.CREATED), HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}")
    @ApiPageable
    public PagedModel<CardAttendanceResource> getByStudent(
            @ApiIgnore @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<CardAttendance> pagedAssembler,
            @PathVariable("studentId") Long studentId
    ) {
        return pagedAssembler.toModel(
                cardAttendanceEndpoint.getByStudent(studentId, pageable),
                new CardAttendanceResourceAssembler()
        );
    }
}
