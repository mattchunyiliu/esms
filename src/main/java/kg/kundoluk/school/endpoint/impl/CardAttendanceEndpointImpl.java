package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.student.CardAttendanceDto;
import kg.kundoluk.school.dto.student.CardAttendanceRequest;
import kg.kundoluk.school.endpoint.CardAttendanceEndpoint;
import kg.kundoluk.school.model.student.CardAttendance;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.rabbit.RabbitMessageProducer;
import kg.kundoluk.school.service.student.CardAttendanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Endpoint
public class CardAttendanceEndpointImpl implements CardAttendanceEndpoint {
    private final CardAttendanceService cardAttendanceService;
    private final StudentRepository studentRepository;
    private final RabbitMessageProducer rabbitMessageProducer;

    public CardAttendanceEndpointImpl(CardAttendanceService cardAttendanceService, StudentRepository studentRepository, RabbitMessageProducer rabbitMessageProducer) {
        this.cardAttendanceService = cardAttendanceService;
        this.studentRepository = studentRepository;
        this.rabbitMessageProducer = rabbitMessageProducer;
    }

    @Override
    public void create(Long studentId, Integer type) {
        CardAttendanceRequest cardAttendanceRequest = CardAttendanceRequest.builder()
                .attendanceDate(LocalDateTime.now())
                .attendanceType(type)
                .student(studentRepository.getOne(studentId))
                .build();
        cardAttendanceService.create(cardAttendanceRequest);

        CardAttendanceDto cardAttendanceDto = new CardAttendanceDto();
        cardAttendanceDto.setStudentId(studentId);
        cardAttendanceDto.setAttendanceType(type);

        rabbitMessageProducer.sendCardAttendancePushNotification(cardAttendanceDto);
    }

    @Override
    public Page<CardAttendance> getByStudent(Long studentId, Pageable pageable) {
        return cardAttendanceService.getByStudent(studentId, pageable);
    }
}
