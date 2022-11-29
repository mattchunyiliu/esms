package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.person.PersonCourseBulkRequest;
import kg.kundoluk.school.dto.person.PersonCourseCreateRequest;
import kg.kundoluk.school.dto.projection.SchoolCourseDTO;
import kg.kundoluk.school.model.school.SchoolCourse;

import java.util.List;
import java.util.Set;

public interface PersonCourseEndpoint {
    void addCourse(PersonCourseCreateRequest personCourseCreateRequest);
    void removeCourse(PersonCourseCreateRequest personCourseCreateRequest);
    void addBulkCourse(PersonCourseBulkRequest personCourseBulkRequest);
    List<SchoolCourseDTO> getAllPersonCourse(Long personId, Long schoolId);
}
