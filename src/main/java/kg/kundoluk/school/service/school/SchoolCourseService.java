package kg.kundoluk.school.service.school;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.dto.school.SchoolCourseBulkRequest;
import kg.kundoluk.school.dto.school.SchoolCourseCreateRequest;
import kg.kundoluk.school.model.school.SchoolClass;
import kg.kundoluk.school.model.school.SchoolCourse;
import kg.kundoluk.school.repository.CourseRepository;
import kg.kundoluk.school.repository.SchoolCourseRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SchoolCourseService {
    private final SchoolCourseRepository schoolCourseRepository;
    private final SchoolRepository schoolRepository;
    private final CourseRepository courseRepository;

    public SchoolCourseService(SchoolCourseRepository schoolCourseRepository, SchoolRepository schoolRepository, CourseRepository courseRepository) {
        this.schoolCourseRepository = schoolCourseRepository;
        this.schoolRepository = schoolRepository;
        this.courseRepository = courseRepository;
    }

    public void delete(@NonNull Long id) {
        this.schoolCourseRepository.deleteById(id);
    }

    public SchoolCourse create(@NonNull SchoolCourseCreateRequest schoolCourseCreateRequest) {
        if (!isExistCourseSchool(schoolCourseCreateRequest.getSchoolId(), schoolCourseCreateRequest.getCourseId())) {
            final SchoolCourse schoolCourse = SchoolCourse.builder()
                    .school(schoolRepository.getOne(schoolCourseCreateRequest.getSchoolId()))
                    .course(courseRepository.getOne(schoolCourseCreateRequest.getCourseId()))
                    .build();
            return this.schoolCourseRepository.save(schoolCourse);
        } return null;
    }

    public void createBulk(SchoolCourseBulkRequest schoolCourseBulkRequest){
        List<SchoolCourse> courses = new ArrayList<>();
        for (Long courseId: schoolCourseBulkRequest.getCourseList()){
            if (!isExistCourseSchool(schoolCourseBulkRequest.getSchoolId(), courseId)) {
                final SchoolCourse schoolCourse = SchoolCourse.builder()
                        .school(schoolRepository.getOne(schoolCourseBulkRequest.getSchoolId()))
                        .course(courseRepository.getOne(courseId))
                        .build();
                courses.add(schoolCourse);
            }
        }
        this.schoolCourseRepository.saveAll(courses);
    }

    public Boolean isExistCourseSchool(Long schoolId, Long courseId){
        return schoolCourseRepository.existsBySchoolIdAndCourseId(schoolId, courseId);
    }

    @Transactional(readOnly = true)
    public List<SchoolCourseDTO> findAllBySchool(Long schoolId){
        return this.schoolCourseRepository.findAllBySchoolId(schoolId);
    }
}
