package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.grade.AttendanceCountDto;
import kg.kundoluk.school.dto.projection.*;

import java.util.List;

public interface GradeAnalyticEndpoint {
    List<GradeStudentAnalytic> findAllByStudent(Long studentId, String start, String end);
    List<GradeStudentAnalytic> findAllByClass(Long classId, Long courseId, String start, String end, Boolean isCourse);
    List<GradeGroupMonthAnalytic> getGradesGroupByClassMonth(Long schoolId, String start, String end);
    AttendanceCountDto getStudentAttendanceCount(Long studentId, String start, String end);
    List<AttendanceReasonCountMonth> getAttendanceGroupReasonMonth(Long schoolId, Long classId, String start, String end);
    List<AttendanceReasonCountClass> getAttendanceGroupReasonQuarter(Long schoolId, Long quarterId);
    List<AttendanceReasonCountStudentMonth> getAttendanceGroupReasonClassMonth(Long classId, String start, String end);
    List<AttendanceReasonCountDay> getAttendanceGroupReasonDay(Long schoolId, String start, String end);
    List<GradeCountAnalytic> getInstructorGradeCount(Long schoolId, String start, String end);
    List<GradeInstructorMonth> getInstructorActivityByMonth(Long schoolId, String start, String end);
    List<GradeSchoolGroupAnalytic> getSchoolGradeCount(Long rayonId, Long townId, Long schoolId, String start, String end);
    List<GradeSchoolGroupAnalytic> getSchoolAttendanceCount(Long rayonId, Long townId, String start, String end);
    List<GradeInstructorClassGroupAnalytic> getInstructorGroupClassGradeCount(Long instructorId, String start, String end);
    List<GradeStudentDate> getInstructorClassGradeList(Long instructorId, Long classId, String start, String end);
    List<AnalyticSchoolCount> getCardAttendanceCountBySchool(String start, String end);
}
