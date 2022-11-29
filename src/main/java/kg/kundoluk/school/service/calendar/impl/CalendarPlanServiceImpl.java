package kg.kundoluk.school.service.calendar.impl;

import kg.kundoluk.school.dto.calendar.CalendarPlanRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.instructor.CalendarPlan;
import kg.kundoluk.school.repository.CalendarPlanRepository;
import kg.kundoluk.school.service.calendar.CalendarPlanService;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CalendarPlanServiceImpl implements CalendarPlanService {
    private final CalendarPlanRepository calendarPlanRepository;

    public CalendarPlanServiceImpl(CalendarPlanRepository calendarPlanRepository) {
        this.calendarPlanRepository = calendarPlanRepository;
    }

    @Override
    public CalendarPlan create(CalendarPlanRequest calendarPlanRequest) {
        CalendarPlan calendarPlan = new CalendarPlan()
                .setChronicleYear(calendarPlanRequest.getChronicleYear())
                .setPerson(calendarPlanRequest.getPerson())
                .setSchoolClass(calendarPlanRequest.getSchoolClass())
                .setSchoolCourse(calendarPlanRequest.getSchoolCourse())
                .setIsDefault(calendarPlanRequest.getIsDefault())
                .setTitle(calendarPlanRequest.getTitle());
        return calendarPlanRepository.save(calendarPlan);
    }

    @Override
    public CalendarPlan edit(CalendarPlanRequest calendarPlanRequest, CalendarPlan calendarPlan) {
        if (calendarPlanRequest.getSchoolClass() != null) {
            calendarPlan.setSchoolClass(calendarPlanRequest.getSchoolClass());
        }
        if (calendarPlanRequest.getSchoolCourse() != null) {
            calendarPlan.setSchoolCourse(calendarPlanRequest.getSchoolCourse());
        }
        return calendarPlanRepository.save(calendarPlan.setTitle(calendarPlanRequest.getTitle()));
    }

    @Override
    public List<CalendarPlan> findAllByInstructorChronicle(Long instructorId, Long chronicleId) {
        return calendarPlanRepository.findAllByPersonIdAndChronicleYearIdAndIsDefaultFalseOrderByCreatedAtDesc(instructorId, chronicleId);
    }

    @Override
    public CalendarPlan findById(Long id) {
        return calendarPlanRepository.findFirstById(id);
    }

    @Override
    public CalendarPlan findByInstructorClassCourseChronicle(Long instructorId, Long classId, Long courseId, Long chronicleId) {
        return calendarPlanRepository.findFirstByPersonIdAndSchoolClassIdAndSchoolCourseIdAndChronicleYearId(instructorId, classId, courseId, chronicleId);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void delete(Long id) throws ConstraintMappingException {

        try {
            calendarPlanRepository.deleteById(id);
        } catch (Exception e){
            String message = "ASSIGMENT_EXIST";
            if (e.getLocalizedMessage().contains("grade_topic_id_fkey"))
                message = "GRADE_EXIST";
            throw new ConstraintMappingException(message);
        }
    }
}
