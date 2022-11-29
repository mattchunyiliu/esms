package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.student.CardAttendanceRequest;
import kg.kundoluk.school.model.student.CardAttendance;
import kg.kundoluk.school.repository.CardAttendanceRepository;
import kg.kundoluk.school.service.student.CardAttendanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardAttendanceServiceImpl implements CardAttendanceService {
    private final CardAttendanceRepository cardAttendanceRepository;

    public CardAttendanceServiceImpl(CardAttendanceRepository cardAttendanceRepository) {
        this.cardAttendanceRepository = cardAttendanceRepository;
    }

    @Override
    public CardAttendance create(CardAttendanceRequest cardAttendanceRequest) {
        return cardAttendanceRepository.save(CardAttendance.builder()
                .attendanceDate(cardAttendanceRequest.getAttendanceDate())
                .attendanceType(cardAttendanceRequest.getAttendanceType())
                .student(cardAttendanceRequest.getStudent())
                .build());
    }

    @Override
    public Page<CardAttendance> getByStudent(Long studentId, Pageable pageable) {
        return cardAttendanceRepository.findAllByStudentId(studentId, pageable);
    }
}
