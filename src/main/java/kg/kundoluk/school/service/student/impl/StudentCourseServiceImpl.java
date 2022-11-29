package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.projection.StudentCourseViewDTO;
import kg.kundoluk.school.dto.report.ClassInstructorCourseDTO;
import kg.kundoluk.school.dto.student.courses.StudentCourseCreateRequest;
import kg.kundoluk.school.dto.student.courses.StudentCourseRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.model.student.StudentCourse;
import kg.kundoluk.school.repository.StudentCourseRepository;
import kg.kundoluk.school.service.student.StudentCourseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class StudentCourseServiceImpl implements StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    public StudentCourseServiceImpl(StudentCourseRepository studentCourseRepository) {
        this.studentCourseRepository = studentCourseRepository;
    }

    @Override
    public StudentCourse create(StudentCourseRequest studentCourseRequest) {
        return studentCourseRepository.save(new StudentCourse()
                .setSchoolClass(studentCourseRequest.getSchoolClass())
                .setArchived(false)
                .setChronicleYear(studentCourseRequest.getChronicleYear())
                .setPerson(studentCourseRequest.getPerson())
                .setSchoolCourse(studentCourseRequest.getSchoolCourse())
                .setStudent(studentCourseRequest.getStudent())
        );
    }

    @Override
    public void createBulk(List<StudentCourseRequest> studentCourseRequestList) throws AlreadyExistException {
        List<StudentCourse> studentCourses = new ArrayList<>();
        for (StudentCourseRequest studentCourseRequest: studentCourseRequestList){
            studentCourses.add(new StudentCourse()
                    .setSchoolClass(studentCourseRequest.getSchoolClass())
                    .setArchived(false)
                    .setChronicleYear(studentCourseRequest.getChronicleYear())
                    .setPerson(studentCourseRequest.getPerson())
                    .setSchoolCourse(studentCourseRequest.getSchoolCourse())
                    .setStudent(studentCourseRequest.getStudent()));
        }
        try {
            studentCourseRepository.saveAll(studentCourses);
        } catch (Exception e){
           log.error("Error while create student course massive - {}", e.getLocalizedMessage());
        }

    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void deleteBulk(StudentCourseCreateRequest studentCourseCreateRequest) {

        List<StudentCourse> studentCourses = studentCourseRepository.findAllByPersonIdAndSchoolClassIdAndSchoolCourseIdAndChronicleYearId(studentCourseCreateRequest.getPersonId(), studentCourseCreateRequest.getClassId(), studentCourseCreateRequest.getCourseId(), studentCourseCreateRequest.getChronicleId());

        if (studentCourseCreateRequest.getStudentId()!=null){ // DELETE STUDENT COURSES ONLY
            studentCourses = studentCourses.stream().filter(sc->sc.getStudent().getId().equals(studentCourseCreateRequest.getStudentId())).collect(Collectors.toList());
        }

        for (StudentCourse studentCourse: studentCourses){

            try {
                studentCourseRepository.delete(studentCourse);
            } catch (Exception e){
                studentCourseRepository.save(studentCourse.setArchived(true));
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentCourse> findAllByStudent(Long studentId, Long chronicleId) {
        return studentCourseRepository.findAllByStudentIdAndChronicleYearId(studentId, chronicleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentCourse> findAllByClass(Long classId, Long chronicleId) {
        return studentCourseRepository.findAllBySchoolClassIdAndChronicleYearId(classId, chronicleId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentCourseViewDTO> findAllByPersonClassChronicleInterface(Long personId, Long courseId, Long classId, Long chronicleId) {
        return studentCourseRepository.findAllByPersonClassCourseChronicleYear(personId, courseId, classId, chronicleId);
    }

    @Override
    public StudentCourse findById(Long id) {
        return studentCourseRepository.findFirstById(id);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public void delete(Long id) throws ConstraintMappingException {
        try {
            studentCourseRepository.deleteById(id);
        } catch (Exception e){
            String message = "QUARTER_GRADE_EXIST";
            if (e.getLocalizedMessage().contains("grade_m2m_student_course_id_fkey"))
                message = "GRADE_EXIST";
            throw new ConstraintMappingException(message);
        }

    }

    @Override
    public List<ClassInstructorCourseDTO> getClassCourseList(Long classId, Long chronicleId) {
        return studentCourseRepository.getClassCourseList(classId, chronicleId);
    }
}
