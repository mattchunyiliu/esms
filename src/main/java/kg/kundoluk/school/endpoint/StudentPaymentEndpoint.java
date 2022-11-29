package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.projection.StudentPremiumCount;
import kg.kundoluk.school.dto.student.StudentSubscriptionRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentCreateRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentResponse;
import kg.kundoluk.school.model.student.StudentPayment;

import java.util.List;

public interface StudentPaymentEndpoint {
    StudentPayment create(StudentPaymentCreateRequest createRequest);
    void editSubscription(StudentSubscriptionRequest studentSubscriptionRequest);
    List<StudentPaymentResponse> findAllByStudent(Long id);
    List<StudentPremiumCount> listPremiumCount();
    List<StudentPaymentResponse> findAll(Long chronicleId);
}
