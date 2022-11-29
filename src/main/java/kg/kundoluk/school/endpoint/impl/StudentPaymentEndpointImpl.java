package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.StudentPremiumCount;
import kg.kundoluk.school.dto.student.StudentSubscriptionRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentCreateRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentRequest;
import kg.kundoluk.school.dto.student.payment.StudentPaymentResponse;
import kg.kundoluk.school.dto.student.payment.StudentSubscription;
import kg.kundoluk.school.endpoint.StudentPaymentEndpoint;
import kg.kundoluk.school.model.student.StudentPayment;
import kg.kundoluk.school.repository.ChronicleRepository;
import kg.kundoluk.school.repository.StudentAnalyticRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;
import kg.kundoluk.school.service.student.StudentPaymentService;
import kg.kundoluk.school.utils.TimeHelper;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class StudentPaymentEndpointImpl implements StudentPaymentEndpoint {
    private final StudentPaymentService studentPaymentService;
    private final StudentAnalyticRepository studentRepository;
    private final ChronicleRepository chronicleRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public StudentPaymentEndpointImpl(StudentPaymentService studentPaymentService, StudentAnalyticRepository studentRepository, ChronicleRepository chronicleRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.studentPaymentService = studentPaymentService;
        this.studentRepository = studentRepository;
        this.chronicleRepository = chronicleRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    @Override
    public StudentPayment create(StudentPaymentCreateRequest createRequest) {
        StudentPaymentRequest studentPaymentRequest = StudentPaymentRequest.builder()
                .amount(createRequest.getAmount())
                .chronicleYear(chronicleRepository.getOne(createRequest.getChronicleId()))
                .description(createRequest.getDescription())
                .student(studentRepository.getOne(createRequest.getStudentId()))
                .build();
        return studentPaymentService.create(studentPaymentRequest);
    }

    @Override
    public void editSubscription(StudentSubscriptionRequest studentSubscriptionRequest) {
        studentPaymentService.updateSubscription(studentSubscriptionRequest.getStudentList(), studentSubscriptionRequest.getSubscriptionType());
        rabbitMessageProducer.sendSubscriptionNotification(new StudentSubscription(studentSubscriptionRequest.getStudentList(), studentSubscriptionRequest.getSubscriptionType()));
    }

    @Override
    public List<StudentPaymentResponse> findAllByStudent(Long id) {
        return studentPaymentService.findAllByStudent(id).stream().map(this::studentPaymentResponse).collect(Collectors.toList());
    }

    @Override
    public List<StudentPremiumCount> listPremiumCount() {
        return studentRepository.getPremiumCount();
    }

    @Override
    public List<StudentPaymentResponse> findAll(Long chronicleId) {
        return studentPaymentService.findAll().stream().map(this::studentPaymentResponse).collect(Collectors.toList());
    }

    private StudentPaymentResponse studentPaymentResponse(StudentPayment studentPayment){
        StudentPaymentResponse response = new StudentPaymentResponse();
        response.setCreatedAt(TimeHelper.DATE_TIME_FORMATTER.format(studentPayment.getCreatedAt()));
        response.setAmount(studentPayment.getAmount());
        response.setDescription(studentPayment.getDescription());
        return response;
    }
}
