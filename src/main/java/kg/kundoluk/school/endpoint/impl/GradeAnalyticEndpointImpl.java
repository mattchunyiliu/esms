package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.grade.AttendanceCountDto;
import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.dto.schedule.ScheduleMonthResponse;
import kg.kundoluk.school.endpoint.GradeAnalyticEndpoint;
import kg.kundoluk.school.endpoint.ScheduleEndpoint;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.repository.CardAttendanceRepository;
import kg.kundoluk.school.repository.GradeAnalyticRepository;
import kg.kundoluk.school.repository.QuarterRepository;
import kg.kundoluk.school.repository.StudentRepository;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;

@Endpoint
public class GradeAnalyticEndpointImpl implements GradeAnalyticEndpoint {
    private final StudentRepository studentRepository;
    private final ScheduleEndpoint scheduleEndpoint;
    private final GradeAnalyticRepository gradeAnalyticRepository;
    private final CardAttendanceRepository cardAttendanceRepository;
    private final QuarterRepository quarterRepository;
    public GradeAnalyticEndpointImpl(StudentRepository studentRepository, ScheduleEndpoint scheduleEndpoint, GradeAnalyticRepository gradeAnalyticRepository, CardAttendanceRepository cardAttendanceRepository, QuarterRepository quarterRepository) {
        this.studentRepository = studentRepository;
        this.scheduleEndpoint = scheduleEndpoint;
        this.gradeAnalyticRepository = gradeAnalyticRepository;
        this.cardAttendanceRepository = cardAttendanceRepository;
        this.quarterRepository = quarterRepository;
    }

    @Override
    public List<GradeStudentAnalytic> findAllByStudent(Long studentId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getAnalyticStudent(studentId, startDate, endDate);
    }

    @Override
    public List<GradeStudentAnalytic> findAllByClass(Long classId, Long courseId, String start, String end, Boolean isCourse) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        if (courseId == null)
            courseId = 0L;

        return !isCourse ?gradeAnalyticRepository.getAnalyticClass(classId, startDate, endDate, courseId):gradeAnalyticRepository.getAnalyticClassGroupCourse(classId, startDate, endDate);
    }

    @Override
    public List<GradeGroupMonthAnalytic> getGradesGroupByClassMonth(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getAnalyticSchoolByMonth(schoolId, startDate, endDate);
    }

    @Override
    public AttendanceCountDto getStudentAttendanceCount(Long studentId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);

        Integer attendance = gradeAnalyticRepository.getStudentAttendanceCount(studentId, startDate, endDate);

        StudentViewDTO studentViewDTO = studentRepository.findViewById(studentId);

        List<ScheduleMonthResponse> scheduleMonthResponses = scheduleEndpoint.findAllClassScheduleMonth(studentViewDTO.getClassId(), 0L, start, end);

        return new AttendanceCountDto(attendance, scheduleMonthResponses.size());
    }

    @Override
    public List<AttendanceReasonCountMonth> getAttendanceGroupReasonMonth(Long schoolId, Long classId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        if (classId == null)
            classId = 0L;
        return gradeAnalyticRepository.getAttendanceGroupReasonMonth(schoolId, classId, startDate, endDate);
    }

    @Override
    public List<AttendanceReasonCountClass> getAttendanceGroupReasonQuarter(Long schoolId, Long quarterId) {
        Quarter quarter = quarterRepository.findById(quarterId).orElseThrow();
        return gradeAnalyticRepository.getAttendanceGroupReasonQuarter(schoolId, quarter.getStartDate(), quarter.getEndDate());
    }

    @Override
    public List<AttendanceReasonCountStudentMonth> getAttendanceGroupReasonClassMonth(Long classId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getAttendanceGroupReasonStudentMonth(classId, startDate, endDate);
    }

    @Override
    public List<AttendanceReasonCountDay> getAttendanceGroupReasonDay(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getAttendanceGroupReasonDay(schoolId, startDate, endDate);
    }

    @Override
    public List<GradeCountAnalytic> getInstructorGradeCount(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getInstructorGradeCount(schoolId, startDate, endDate);
    }

    @Override
    public List<GradeInstructorMonth> getInstructorActivityByMonth(Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getInstructorActivityMonth(schoolId, startDate, endDate);
    }

    @Override
    public List<GradeSchoolGroupAnalytic> getSchoolGradeCount(Long rayonId, Long townId, Long schoolId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        if (rayonId == null)
            rayonId = 0L;
        if (townId == null)
            townId = 0L;
        if (schoolId == null)
            schoolId = 0L;
        return gradeAnalyticRepository.getSchoolGradeCount(rayonId, townId, schoolId, startDate, endDate);
    }

    @Override
    public List<GradeSchoolGroupAnalytic> getSchoolAttendanceCount(Long rayonId, Long townId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        if (rayonId == null)
            rayonId = 0L;
        if (townId == null)
            townId = 0L;
        return gradeAnalyticRepository.getSchoolAttendanceCount(rayonId, townId, startDate, endDate);
    }

    @Override
    public List<GradeInstructorClassGroupAnalytic> getInstructorGroupClassGradeCount(Long instructorId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getInstructorGroupClassGradeCount(instructorId, startDate, endDate);
    }

    @Override
    public List<GradeStudentDate> getInstructorClassGradeList(Long instructorId, Long classId, String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return gradeAnalyticRepository.getInstructorClassGradeList(instructorId, classId, startDate, endDate);
    }

    @Override
    public List<AnalyticSchoolCount> getCardAttendanceCountBySchool(String start, String end) {
        LocalDate startDate = LocalDate.parse(start, TimeHelper.DATE_REVERSE_FORMATTER);
        LocalDate endDate = LocalDate.parse(end, TimeHelper.DATE_REVERSE_FORMATTER);
        return cardAttendanceRepository.getCardAttendanceBySchool(startDate, endDate);
    }
}
