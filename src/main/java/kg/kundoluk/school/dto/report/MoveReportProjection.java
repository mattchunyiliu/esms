package kg.kundoluk.school.dto.report;

public interface MoveReportProjection {
    Long getId();
    Long getQuarterId();
    Long getClassId();
    Integer getGender();
    Integer getCount();
    Integer getInitialCount();
    Integer getFinalCount();
    Integer getDepCountryCount();
    Integer getDepRegionCount();
    Integer getDepRayonCount();
    Integer getArrCountryCount();
    Integer getArrRegionCount();
    Integer getArrRayonCount();
}
