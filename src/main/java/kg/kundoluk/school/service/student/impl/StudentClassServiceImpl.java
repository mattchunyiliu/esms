package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.student.StudentClassRequest;
import kg.kundoluk.school.model.student.StudentClass;
import kg.kundoluk.school.repository.StudentClassRepository;
import kg.kundoluk.school.service.student.StudentClassService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentClassServiceImpl implements StudentClassService {
    private final StudentClassRepository studentClassRepository;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository) {
        this.studentClassRepository = studentClassRepository;
    }

    @Override
    public StudentClass create(StudentClassRequest studentClassRequest) {
        if (!check(studentClassRequest)) {
            return studentClassRepository.save(
                    new StudentClass()
                            .setStudent(studentClassRequest.getStudent())
                            .setSchoolClass(studentClassRequest.getSchoolClass())
                            .setChronicleYear(studentClassRequest.getChronicleYear())
            );
        } return null;
    }

    @Override
    public void delete(Long id) {
        studentClassRepository.deleteById(id);
    }

    @Override
    public List<StudentClass> findAllByStudent(Long studentId) {
        return studentClassRepository.findAllByStudentId(studentId);
    }

    private Boolean check(StudentClassRequest studentClassRequest){
        return studentClassRepository.existsByChronicleYearAndSchoolClassAndStudent(studentClassRequest.getChronicleYear(), studentClassRequest.getSchoolClass(), studentClassRequest.getStudent());
    }
}
