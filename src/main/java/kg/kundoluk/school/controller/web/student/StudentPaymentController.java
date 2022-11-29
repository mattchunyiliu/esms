package kg.kundoluk.school.controller.web.student;

import kg.kundoluk.school.dto.ApiResponse;
import kg.kundoluk.school.dto.projection.StudentPremiumCount;
import kg.kundoluk.school.dto.student.StudentSubscriptionRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentCreateRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentResponse;
import kg.kundoluk.school.endpoint.StudentPaymentEndpoint;
import kg.kundoluk.school.model.student.StudentPayment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/web/v1/student/payment")
public class StudentPaymentController {
    private final StudentPaymentEndpoint studentPaymentEndpoint;

    public StudentPaymentController(StudentPaymentEndpoint studentPaymentEndpoint) {
        this.studentPaymentEndpoint = studentPaymentEndpoint;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody StudentPaymentCreateRequest studentCourseCreateRequest
    ) {
        studentPaymentEndpoint.create(studentCourseCreateRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @PostMapping("/subscription/edit")
    public ResponseEntity<?> editSubscription(
            @Valid @RequestBody StudentSubscriptionRequest studentSubscriptionRequest
    ) {
        studentPaymentEndpoint.editSubscription(studentSubscriptionRequest);
        return new ResponseEntity<>(new ApiResponse(true, "CREATED"), HttpStatus.OK);
    }

    @GetMapping("/student/{studentId}")
    public List<StudentPaymentResponse> findAllByStudent(@PathVariable("studentId") Long studentId) {
        return studentPaymentEndpoint.findAllByStudent(studentId);
    }

    @GetMapping("/premium")
    public List<StudentPremiumCount> findAllPremiumCountBySchool() {
        return studentPaymentEndpoint.listPremiumCount();
    }

    @GetMapping("chronicle/{chronicleId}")
    public List<StudentPaymentResponse> findAll(@PathVariable("chronicleId") Long chronicleId) {
        return studentPaymentEndpoint.findAll(chronicleId);
    }
}
