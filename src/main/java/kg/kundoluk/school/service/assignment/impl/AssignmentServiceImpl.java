package kg.kundoluk.school.service.assignment.impl;

import kg.kundoluk.school.dto.assignment.AssignmentRequest;
import kg.kundoluk.school.model.instructor.Assignment;
import kg.kundoluk.school.repository.AssignmentRepository;
import kg.kundoluk.school.service.assignment.AssignmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment create(AssignmentRequest assignmentRequest) {
        return assignmentRepository.save(
                new Assignment()
                        .setCalendarTopic(assignmentRequest.getCalendarTopic())
                        .setTitle(assignmentRequest.getTitle())
                        .setDeadlineAt(assignmentRequest.getDeadlineAt())
                        .setContent(assignmentRequest.getContent()));
    }

    @Override
    public Assignment edit(AssignmentRequest assignmentRequest, Assignment assignment) {
        return assignmentRepository.save(
                assignment
                        .setCalendarTopic(assignmentRequest.getCalendarTopic())
                        .setTitle(assignmentRequest.getTitle())
                        .setDeadlineAt(assignmentRequest.getDeadlineAt())
                        .setContent(assignmentRequest.getContent()));
    }

    @Override
    public Assignment findById(Long id) {
        return assignmentRepository.findFirstById(id);
    }

    @Override
    public void delete(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Assignment> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId, Pageable pageable) {
        return assignmentRepository.findAllByCalendarTopic_CalendarPlan_PersonId(instructorId, classId, courseId, chronicleId, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Assignment> findAllByClass(Long classId, Long chronicleId, Pageable pageable) {
        return assignmentRepository.findAllByClassId(classId, chronicleId, pageable);
    }
}
