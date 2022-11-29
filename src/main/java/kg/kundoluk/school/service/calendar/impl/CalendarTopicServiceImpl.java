package kg.kundoluk.school.service.calendar.impl;

import kg.kundoluk.school.dto.calendar.CalendarTopicRequest;
import kg.kundoluk.school.dto.projection.CalendarTopicDTO;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarTopic;
import kg.kundoluk.school.repository.CalendarTopicRepository;
import kg.kundoluk.school.service.calendar.CalendarTopicService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class CalendarTopicServiceImpl implements CalendarTopicService {
    private final CalendarTopicRepository calendarTopicRepository;

    public CalendarTopicServiceImpl(CalendarTopicRepository calendarTopicRepository) {
        this.calendarTopicRepository = calendarTopicRepository;
    }

    @Override
    public CalendarTopic create(CalendarTopicRequest calendarTopicRequest) {
        CalendarTopic calendarTopic = new CalendarTopic()
                .setCalendarPlan(calendarTopicRequest.getCalendarPlan())
                .setTopicResult(calendarTopicRequest.getTopicResult())
                .setTopicVisibility(calendarTopicRequest.getTopicVisibility())
                .setDatePlan(calendarTopicRequest.getDatePlan())
                .setHours(calendarTopicRequest.getHours())
                .setQuarter(calendarTopicRequest.getQuarter())
                .setTitle(calendarTopicRequest.getTitle());
        return calendarTopicRepository.save(calendarTopic);
    }

    @Override
    public CalendarTopic edit(CalendarTopicRequest calendarTopicRequest, CalendarTopic calendarTopic) {
        return calendarTopicRepository.save( calendarTopic
                .setTopicResult(calendarTopicRequest.getTopicResult())
                .setIsPassed(calendarTopicRequest.getIsPassed())
                .setTopicVisibility(calendarTopicRequest.getTopicVisibility())
                .setDatePlan(calendarTopicRequest.getDatePlan())
                .setHours(calendarTopicRequest.getHours())
                .setQuarter(calendarTopicRequest.getQuarter())
                .setTitle(calendarTopicRequest.getTitle()));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CalendarTopic> findAllByCalendarPlan(Long id) {
        return calendarTopicRepository.findAllByCalendarPlanId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CalendarTopicDTO> findAllByInstructorClassCourse(Long instructorId, Long classId, Long courseId, Long chronicleId) {
        return calendarTopicRepository.findAllByInstructorClassCourse(instructorId, classId, courseId, chronicleId);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void delete(Long id) throws ConstraintMappingException {
        try {
            calendarTopicRepository.deleteById(id);
        } catch (Exception e){
            String message = "ASSIGMENT_EXIST";
            if (e.getLocalizedMessage().contains("grade_topic_id_fkey"))
                message = "GRADE_EXIST";
            throw new ConstraintMappingException(message);
        }

    }

    @Transactional(readOnly = true)
    @Override
    public CalendarTopic findById(Long id) {
        return calendarTopicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
