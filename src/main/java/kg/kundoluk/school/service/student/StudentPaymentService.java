package kg.kundoluk.school.service.student;

import kg.kundoluk.school.dto.student.payment.StudentPaymentRequest;
import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.student.StudentPayment;

import java.util.List;

public interface StudentPaymentService {
    StudentPayment create(StudentPaymentRequest studentPaymentRequest);
    void updateSubscription(List<Long> studentList, SubscriptionType subscriptionType);
    List<StudentPayment> findAllByStudent(Long id);
    List<StudentPayment> findAll();
}
