package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.projection.*;
import kg.kundoluk.school.dto.report.*;
import kg.kundoluk.school.endpoint.QuarterGradeAnalyticEndpoint;
import kg.kundoluk.school.model.enums.QuarterGradeMarkType;
import kg.kundoluk.school.repository.QuarterGradeAnalyticRepository;
import kg.kundoluk.school.repository.StudentCourseRepository;
import kg.kundoluk.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class QuarterGradeAnalyticEndpointImpl implements QuarterGradeAnalyticEndpoint {
    private final QuarterGradeAnalyticRepository quarterGradeAnalyticRepository;
    private final StudentRepository studentRepository;
    private final StudentCourseRepository studentCourseRepository;

    public QuarterGradeAnalyticEndpointImpl(QuarterGradeAnalyticRepository quarterGradeAnalyticRepository, StudentRepository studentRepository, StudentCourseRepository studentCourseRepository) {
        this.quarterGradeAnalyticRepository = quarterGradeAnalyticRepository;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    @Override
    public List<QuarterGradeStudentAnalyticDTO> getAllStudentAnalytic(Long classId, Long quarterId, Long courseId, QuarterGradeMarkType quarterGradeMarkType, Long chronicleId) {
        if (quarterId == null)
            quarterId = 0L;
        if (courseId == null)
            courseId = 0L;
        int gradeType = quarterGradeMarkType == null ? -1 : quarterGradeMarkType.ordinal();
        return quarterGradeAnalyticRepository.getAllStudentAnalytic(classId, quarterId, gradeType, chronicleId, courseId);
    }

    @Override
    public List<QuarterGradeGroupMarkDTO> getStudentGroupByMark(Long schoolId, Long classId, Long quarterId) {
        if (schoolId == null)
            schoolId = 0L;
        if (classId == null)
            classId = 0L;
        return quarterGradeAnalyticRepository.getStudentGroupByMark(schoolId, classId, quarterId);
    }

    @Override
    public List<QuarterGradeStudentGroupMarkDTO> getStudentsGroupByMark(Long schoolId, Long classId, Long quarterId) {
        if (schoolId == null)
            schoolId = 0L;
        if (classId == null)
            classId = 0L;
        return quarterGradeAnalyticRepository.getStudentsGroupByMark(schoolId, classId, quarterId);
    }

    @Override
    public List<GradeClassGroupAverage> getClassAverageGrade(Long schoolId, Long quarterId) {
        return quarterGradeAnalyticRepository.getClassAverageGrade(schoolId, quarterId);
    }

    @Override
    public ClassQuarterGradeReportResponse getClassPerformance(Long classId, Long quarterId, QuarterGradeMarkType quarterGradeMarkType, Long chronicleId) {

        List<StudentViewMobileDTO> students = studentRepository.findAllBySchoolClass(classId);
        List<QuarterGradeStudentAnalyticDTO> grades = getAllStudentAnalytic(classId, quarterId, 0L, quarterGradeMarkType, chronicleId);
        List<ClassInstructorCourseDTO> classCourseList = studentCourseRepository.getClassCourseList(classId, chronicleId);

        // student performance
        List<StudentPerformanceReport> studentPerformanceReports = getStudentPerformanceReports(students, grades);

        // instructor performance
        List<InstructorCoursePerformanceReport> instructorCoursePerformanceReports = getInstructorCoursePerformanceReports(grades, classCourseList);

        return new ClassQuarterGradeReportResponse(studentPerformanceReports, instructorCoursePerformanceReports);
    }

    private List<StudentPerformanceReport> getStudentPerformanceReports(List<StudentViewMobileDTO> students, List<QuarterGradeStudentAnalyticDTO> grades) {
        List<StudentPerformanceReport> studentPerformanceReports = new ArrayList<>();
        for (StudentViewMobileDTO student : students){
            int totalFive = 0,  totalFour = 0, totalThree = 0, totalTwo = 0, count = 0, mark = 0;

            List<QuarterGradeStudentAnalyticDTO> studentGrades = grades.stream().filter(g->g.getStudentId().equals(student.getId())).collect(Collectors.toList());

            List<StudentCourseQuarterGrade> courseQuarterGradeList = new ArrayList<>();
            for (QuarterGradeStudentAnalyticDTO quarterGrade: studentGrades){
                StudentCourseQuarterGrade studentCourseQuarterGrade = new StudentCourseQuarterGrade(quarterGrade);
                if(quarterGrade.getQuarterMark() != null) {
                    mark = mark + Integer.parseInt(quarterGrade.getQuarterMark());
                    count = count + 1;
                    switch (quarterGrade.getQuarterMark()) {
                        case "5":
                            totalFive++;
                            break;
                        case "4":
                            totalFour++;
                            break;
                        case "3":
                            totalThree++;
                            break;
                        case "2":
                            totalTwo++;
                            break;
                    }
                }
                courseQuarterGradeList.add(studentCourseQuarterGrade);
            }

            StudentPerformanceReport studentPerformanceReport = getStudentPerformanceReport(student, totalFive, totalFour, totalThree, totalTwo, count, mark, courseQuarterGradeList);

            studentPerformanceReports.add(studentPerformanceReport);
        }
        return studentPerformanceReports;
    }

    private List<InstructorCoursePerformanceReport> getInstructorCoursePerformanceReports(List<QuarterGradeStudentAnalyticDTO> grades, List<ClassInstructorCourseDTO> classCourseList) {
        List<InstructorCoursePerformanceReport> instructorCoursePerformanceReports = new ArrayList<>();
        for (ClassInstructorCourseDTO instructorCourse: classCourseList){
            int totalFive = 0,  totalFour = 0, totalThree = 0, totalTwo = 0, count = 0, mark = 0;
            List<QuarterGradeStudentAnalyticDTO> instructorGrades = grades.stream().filter(g->g.getInstructorId().equals(instructorCourse.getInstructorId()) && g.getCourseId().equals(instructorCourse.getCourseId())).collect(Collectors.toList());
            for (QuarterGradeStudentAnalyticDTO quarterGrade: instructorGrades){
                if(quarterGrade.getQuarterMark() != null) {
                    mark = mark + Integer.parseInt(quarterGrade.getQuarterMark());
                    count = count + 1;
                    switch (quarterGrade.getQuarterMark()) {
                        case "5":
                            totalFive++;
                            break;
                        case "4":
                            totalFour++;
                            break;
                        case "3":
                            totalThree++;
                            break;
                        case "2":
                            totalTwo++;
                            break;
                    }
                }
            }
            InstructorCoursePerformanceReport instructorCoursePerformanceReport = getInstructorCoursePerformanceReport(instructorCourse, totalFive, totalFour, totalThree, totalTwo, count);

            instructorCoursePerformanceReports.add(instructorCoursePerformanceReport);

        }
        return instructorCoursePerformanceReports;
    }

    private InstructorCoursePerformanceReport getInstructorCoursePerformanceReport(ClassInstructorCourseDTO instructorCourse, int totalFive, int totalFour, int totalThree, int totalTwo, int count) {
        InstructorCoursePerformanceReport instructorCoursePerformanceReport = new InstructorCoursePerformanceReport(instructorCourse);
        int mixed = totalFive + totalFour + totalThree;
        int achievement = 0;
        int quality = 0;
        if (count != 0) {
            achievement = (int) Math.round((double) mixed * 100 / count);
            quality = (int) Math.round((double)(totalFive + totalFour)*100/ count);
        }
        instructorCoursePerformanceReport.setAchievement(achievement);
        instructorCoursePerformanceReport.setQuality(quality);
        instructorCoursePerformanceReport.setTotalFive(totalFive);
        instructorCoursePerformanceReport.setTotalFour(totalFour);
        instructorCoursePerformanceReport.setTotalThree(totalThree);
        instructorCoursePerformanceReport.setTotalTwo(totalTwo);
        return instructorCoursePerformanceReport;
    }

    private StudentPerformanceReport getStudentPerformanceReport(StudentViewMobileDTO student, int totalFive, int totalFour, int totalThree, int totalTwo, int count, int mark, List<StudentCourseQuarterGrade> courseQuarterGradeList) {
        StudentPerformanceReport studentPerformanceReport = new StudentPerformanceReport(student);
        studentPerformanceReport.setCourseDtoList(courseQuarterGradeList);
        studentPerformanceReport.setAverageGrade(String.valueOf(mark * 1.0 / count));
        int mixed = totalFive + totalFour + totalThree;
        int achievement = 0;
        int quality = 0;
        if (count != 0) {
            achievement = (int) Math.round((double) mixed * 100 / count);
            quality = (int) Math.round((double)(totalFive + totalFour)*100/ count);
        }
        studentPerformanceReport.setAchievement(achievement);
        studentPerformanceReport.setQuality(quality);
        studentPerformanceReport.setTotalFive(totalFive);
        studentPerformanceReport.setTotalFour(totalFour);
        studentPerformanceReport.setTotalThree(totalThree);
        studentPerformanceReport.setTotalTwo(totalTwo);
        return studentPerformanceReport;
    }
}
