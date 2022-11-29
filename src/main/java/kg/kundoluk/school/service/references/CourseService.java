package kg.kundoluk.school.service.references;

import kg.kundoluk.school.dto.course.CourseRequest;
import kg.kundoluk.school.model.references.Course;
import kg.kundoluk.school.repository.CourseRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    //@CacheEvict(value = CacheService.COURSE)
    public Course create(CourseRequest courseRequest) {
        return courseRepository.save(
                new Course()
                        .setTitle(courseRequest.getTitle())
                        .setColorHex(courseRequest.getColorHex())
                        .setCourseType(courseRequest.getCourseType())
                        .setTitleRu(courseRequest.getTitleRu())
                        .setTitleKg(courseRequest.getTitleKg())
                        .setTitleTr(courseRequest.getTitleTr())
        );
    }

    //@CacheEvict(value = CacheService.COURSE)
    public Course edit(Course src, CourseRequest dst) {
        return courseRepository.save(
                src
                        .setColorHex(dst.getColorHex())
                        .setCourseType(dst.getCourseType())
                        .setTitle(dst.getTitle())
                        .setTitleKg(dst.getTitleKg())
                        .setTitleTr(dst.getTitleTr())
                        .setTitleRu(dst.getTitleRu())
                        .setTitleRu(dst.getTitleRu())
        );
    }

    //@Cacheable(value = CacheService.COURSE)
    public List<Course> list() {
        return courseRepository.findAll();
    }

    public Course findById(Long id){
        return courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
