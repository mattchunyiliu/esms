package kg.kundoluk.school.service.async.impl;

import kg.kundoluk.school.dto.projection.StudentViewMobileDTO;
import kg.kundoluk.school.dto.student.StudentClassRequest;
import kg.kundoluk.school.dto.student.StudentRequest;
import kg.kundoluk.school.exception.AlreadyExistException;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.student.Student;
import kg.kundoluk.school.service.references.ChronicleService;
import kg.kundoluk.school.service.async.StudentAdminService;
import kg.kundoluk.school.service.async.StudentCourseGenerateService;
import kg.kundoluk.school.service.school.SchoolClassService;
import kg.kundoluk.school.service.school.SchoolPresetService;
import kg.kundoluk.school.service.student.StudentClassService;
import kg.kundoluk.school.service.student.StudentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentAdminServiceImpl implements StudentAdminService {
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final StudentClassService studentClassService;
    private final ChronicleService chronicleService;
    private final SchoolPresetService schoolPresetService;
    private final StudentCourseGenerateService studentCourseGenerateService;

    public StudentAdminServiceImpl(StudentService studentService, SchoolClassService schoolClassService, StudentClassService studentClassService, ChronicleService chronicleService, SchoolPresetService schoolPresetService, StudentCourseGenerateService studentCourseGenerateService) {
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.studentClassService = studentClassService;
        this.chronicleService = chronicleService;
        this.schoolPresetService = schoolPresetService;
        this.studentCourseGenerateService = studentCourseGenerateService;
    }

    @Async("asyncExecutor")
    @Override
    public void changeLevel(Long schoolId, Long classId, Long chronicleId, Boolean isRaise) throws AlreadyExistException {
        List<SchoolClass> schoolClasses = schoolClassService.findAllBySchool(schoolId);
        schoolClasses.sort(Comparator.comparing(SchoolClass::getLevel).reversed());

        //versionOne(classId, isRaise, schoolClasses);
        versionTwo(schoolId, isRaise, schoolClasses);
    }

    private void versionOne(Long classId, Boolean isRaise, List<SchoolClass> schoolClasses) {
        if (classId != null){
            Set<Student> students = studentService.findAllByClass(classId);
            SchoolClass schoolClass = schoolClassService.findById(classId);
            changeStudentsClassLevel(isRaise, schoolClasses, schoolClass, students);
        } else {
            List<Long> raisedClassIds = new ArrayList<>();
            for (SchoolClass schoolClass : schoolClasses) {
                if (raisedClassIds.stream().noneMatch(l->l.equals(schoolClass.getId()))) {
                    Set<Student> students = studentService.findAllByClass(schoolClass.getId());
                    changeStudentsClassLevel(isRaise, schoolClasses, schoolClass, students);
                }
                raisedClassIds.add(schoolClass.getId());
            }
        }
    }

    private void versionTwo(Long schoolId, Boolean isRaise, List<SchoolClass> schoolClasses) {
        Set<Student> students = studentService.findAllBySchool(schoolId);
        for (Student student: students){
            SchoolClass schoolClass = student.getSchoolClass();
            int level = schoolClass.getLevel();
            String label = schoolClass.getLabel();
            if (isRaise) {
                level = level + 1;
            } else {
                level = level - 1;
            }
            updateStudentClass(schoolClasses, student, level, label);
        }
    }

    @Override
    public List<StudentViewMobileDTO> getByClass(Long classId) {
        return studentService.getStudentListByClass(classId);
    }

    @Async("asyncExecutor")
    @Override
    public void generateClassCourses(Long classId, Long chronicleId) throws AlreadyExistException {
        List<StudentViewMobileDTO> students = studentService.getStudentListByClass(classId);
        for (StudentViewMobileDTO student: students){
            studentCourseGenerateService.generateStudentCourse(student.getId(), classId);
        }
    }

    private void changeStudentsClassLevel(Boolean isRaise, List<SchoolClass> schoolClasses, SchoolClass schoolClass, Set<Student> students) {
        int level = schoolClass.getLevel();
        String label = schoolClass.getLabel();
        if (isRaise) {
            level = level + 1;
        } else {
            level = level - 1;
        }
        for (Student student : students) {
            updateStudentClass(schoolClasses, student, level, label);
        }
    }

    private void updateStudentClass(List<SchoolClass> schoolClasses, Student student, int level, String label) {

        Optional<SchoolClass> aClass = schoolClasses
                .stream()
                .filter(cl -> cl.getLevel().equals(level) && cl.getLabel().equalsIgnoreCase(label))
                .findAny();
        boolean archived = level > schoolClasses.get(0).getLevel(); // First will be max level;
        StudentRequest studentRequest;
        if (aClass.isPresent()) {
            studentRequest = StudentRequest.builder()
                    .schoolClass(aClass.get())
                    .archived(archived)
                    .build();

            StudentClassRequest studentClassRequest = StudentClassRequest.builder()
                    .chronicleYear(chronicleService.getActive())
                    .student(student)
                    .schoolClass(aClass.get())
                    .build();
            studentClassService.create(studentClassRequest);

        } else {
            studentRequest = StudentRequest.builder()
                    .archived(archived)
                    .build();
        }
        studentService.edit(studentRequest, student);
    }
}
