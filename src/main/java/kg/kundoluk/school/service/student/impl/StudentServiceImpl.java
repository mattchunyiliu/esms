package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.projection.StudentViewDTO;
import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.StudentRequest;
import kg.kundoluk.school.exception.ConstraintMappingException;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.service.student.StudentService;
import kg.kundoluk.school.utils.UpdateColumnUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(StudentRequest studentRequest) {
        return saveOrUpdate(studentRequest, new Student());
    }

    @Override
    public Student edit(StudentRequest studentRequest, Student student) {
        BeanUtils.copyProperties(studentRequest, student, UpdateColumnUtil.getNullPropertyNames(studentRequest));
        return studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findFirstById(id);
    }

    @Override
    public StudentViewDTO findViewById(Long id) {
        return studentRepository.findViewById(id);
    }

    @Override
    public Student findByUserId(Long id) {
        return studentRepository.findFirstByUserId(id);
    }

    @Override
    public void archive(Long studentId, Boolean status) {
        studentRepository.updateArchived(studentId, status);
    }

    @Override
    public StudentViewMobileDTO getStudentSchoolByTitle(String firstName, String lastName, String middleName, Integer gender, LocalDate dob) {
        if (StringUtils.isEmpty(middleName))
            middleName = "-";
        return studentRepository.filterByTitleSQL(firstName.toLowerCase(), lastName.toLowerCase(), middleName.toLowerCase(), gender, dob);
    }

    @Transactional(propagation = Propagation.NEVER)
    @Override
    public Boolean delete(Student student) throws ConstraintMappingException {
        try {
            studentRepository.delete(student);
            return true;
        }catch (Exception e){
           studentRepository.save(student.setArchived(true));
           String message = "STUDENT_COURSE_EXIST";
           if (e.getLocalizedMessage().contains("grade_m2m_student_course_id_fkey")) {
               message = "GRADE_EXIST";
           }
            throw new ConstraintMappingException(message);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentViewDTO> getStudentListBySchool(Long schoolId, Boolean archived) {
        return studentRepository.findAllBySchool(schoolId, archived);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentViewMobileDTO> getStudentListByClass(Long classId) {
        return studentRepository.findAllBySchoolClass(classId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentViewMobileDTO> getStudentListWithParentByClass(Long classId) {
        return studentRepository.findAllBySchoolClassWithParent(classId);
    }

    @Override
    public Set<Student> findAllByClass(Long classId) {
        return studentRepository.findAllBySchoolClassId(classId);
    }

    @Override
    public Set<Student> findAllBySchool(Long schoolId) {
        return studentRepository.findAllBySchoolId(schoolId);
    }

    @Override
    public Set<Student> findAllByIdList(List<Long> ids) {
        return studentRepository.findAllByIdIn(ids);
    }

    @Override
    public List<Student> searchStudent(String firstName, String lastName, String middleName) {
        return studentRepository.searchStudent(firstName, lastName, middleName);
    }

    private Student saveOrUpdate(StudentRequest studentRequest, Student student){
        if (studentRequest.getUser()!=null)
            student.setUser(studentRequest.getUser());
        student
                .setSchoolClass(studentRequest.getSchoolClass())
                .setArchived(false)
                .setSubscriptionType(studentRequest.getSubscriptionType())
                .setAvatar(studentRequest.getAvatar())
                .setNationality(studentRequest.getNationality())
                .setFirstName(studentRequest.getFirstName())
                .setLastName(studentRequest.getLastName())
                .setMiddleName(studentRequest.getMiddleName())
                .setGender(studentRequest.getGender())
                .setBirthday(studentRequest.getBirthday());

        return studentRepository.save(student);
    }
}
