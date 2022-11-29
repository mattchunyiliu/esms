package kg.kundoluk.school.service.student.impl;

import kg.kundoluk.school.dto.projection.FirebaseTokenDTO;
import kg.kundoluk.school.dto.student.parent.StudentParentDto;
import kg.kundoluk.school.dto.student.parent.StudentParentRequest;
import kg.kundoluk.school.model.student.StudentParent;
import kg.kundoluk.school.repository.StudentParentRepository;
import kg.kundoluk.school.service.student.StudentParentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service
public class StudentParentServiceImpl implements StudentParentService {
    private final StudentParentRepository studentParentRepository;

    public StudentParentServiceImpl(StudentParentRepository studentParentRepository) {
        this.studentParentRepository = studentParentRepository;
    }

    @Override
    public StudentParent create(StudentParentRequest studentParentRequest) {
        StudentParent studentParent = new StudentParent()
                .setParent(studentParentRequest.getPerson())
                .setStudent(studentParentRequest.getStudent())
                .setParentalType(studentParentRequest.getParentalType());
        return studentParentRepository.save(studentParent);
    }

    @Override
    public StudentParent edit(StudentParentRequest studentParentRequest, StudentParent studentParent) {
        return studentParentRepository.save(studentParent.setParent(studentParentRequest.getPerson()).setParentalType(studentParentRequest.getParentalType()));
    }

    @Override
    public void modifySQL(StudentParentDto studentParentDto) {
        studentParentRepository.updateParent(studentParentDto.getId(), studentParentDto.getParentId(), studentParentDto.getParentalType());
    }

    @Override
    public StudentParent findById(Long id) {
        return studentParentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentParent> findAllByStudent(Long studentId) {
        return studentParentRepository.findAllByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentParent> findAllByClass(Long classId) {
        return studentParentRepository.findAllByStudentSchoolClassId(classId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentParent> findAllByParent(Long parentId) {
        return studentParentRepository.findAllByParentId(parentId);
    }

    @Override
    public List<StudentParent> findAllByParentUsername(String username) {
        return studentParentRepository.findAllByParentUserUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FirebaseTokenDTO> findParentTokensByStudent(Long studentId) {
        return studentParentRepository.findParentTokensByStudent(studentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FirebaseTokenDTO> findParentTokensByClass(Long classId) {
        return studentParentRepository.findParentTokensByClass(classId);
    }

    @Override
    public void delete(Long id) {
        studentParentRepository.deleteById(id);
    }
}
