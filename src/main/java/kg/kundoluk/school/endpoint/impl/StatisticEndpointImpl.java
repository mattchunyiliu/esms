package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.AnalyticClassGender;
import kg.kundoluk.school.dto.projection.AnalyticGender;
import kg.kundoluk.school.endpoint.StatisticEndpoint;
import kg.kundoluk.school.repository.PersonAnalyticRepository;
import kg.kundoluk.school.repository.PersonRepository;
import kg.kundoluk.school.repository.StudentAnalyticRepository;
import kg.kundoluk.school.repository.StudentRepository;

import java.util.List;

@Endpoint
public class StatisticEndpointImpl implements StatisticEndpoint {
    private final StudentAnalyticRepository studentRepository;
    private final PersonAnalyticRepository personRepository;

    public StatisticEndpointImpl(StudentAnalyticRepository studentRepository, PersonAnalyticRepository personRepository) {
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<AnalyticGender> getAllStudentGenderAnalytic(Long schoolId, Long rayonId, Long townId) {
        if (schoolId == null)
            schoolId = 0L;
        if (rayonId == null)
            rayonId = 0L;
        if (townId == null)
            townId = 0L;
        return studentRepository.getStudentGenderAnalytic(schoolId, rayonId, townId);
    }

    @Override
    public List<AnalyticGender> getAllInstructorGenderAnalytic(Long schoolId, Long rayonId, Long townId) {
        if (schoolId == null)
            schoolId = 0L;
        if (rayonId == null)
            rayonId = 0L;
        if (townId == null)
            townId = 0L;
        return personRepository.getInstructorGenderAnalytic(schoolId, rayonId, townId);
    }

    @Override
    public List<AnalyticGender> getAllParentGenderAnalytic(Long schoolId) {
        if (schoolId == null)
            schoolId = 0L;
        return personRepository.getParentGenderAnalytic(schoolId);
    }

    @Override
    public List<AnalyticClassGender> getSchoolClassGenderAnalytic(Long schoolId,  Long classId) {
        if (classId == null)
            classId = 0L;
        return studentRepository.getStudentClassGenderAnalytic(schoolId, classId);
    }
}
