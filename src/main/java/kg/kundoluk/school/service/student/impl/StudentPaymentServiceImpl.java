package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.student.payment.StudentPaymentRequest;
import kg.kundoluk.school.model.enums.SubscriptionType;
import kg.kundoluk.school.model.student.StudentPayment;
import kg.kundoluk.school.repository.StudentPaymentRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.student.StudentPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentPaymentServiceImpl implements StudentPaymentService {
    private final StudentPaymentRepository studentPaymentRepository;
    private final StudentRepository studentRepository;

    public StudentPaymentServiceImpl(StudentPaymentRepository studentPaymentRepository, StudentRepository studentRepository) {
        this.studentPaymentRepository = studentPaymentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentPayment create(StudentPaymentRequest studentPaymentRequest) {
        return studentPaymentRepository.save(
                new StudentPayment()
                        .setAmount(studentPaymentRequest.getAmount())
                        .setChronicleYear(studentPaymentRequest.getChronicleYear())
                        .setDescription(studentPaymentRequest.getDescription())
                        .setStudent(studentPaymentRequest.getStudent())
        );
    }

    @Override
    public void updateSubscription(List<Long> studentList, SubscriptionType subscriptionType) {
        studentRepository.updateSubscription(studentList, subscriptionType.ordinal());
    }

    @Override
    public List<StudentPayment> findAllByStudent(Long id) {
        return studentPaymentRepository.findAllByStudentIdOrderByCreatedAtDesc(id);
    }

    @Override
    public List<StudentPayment> findAll() {
        return studentPaymentRepository.findAll();
    }
}
