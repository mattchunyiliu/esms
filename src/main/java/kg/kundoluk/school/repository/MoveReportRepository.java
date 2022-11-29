package kg.kundoluk.school.repository;

import kg.kundoluk.school.dto.report.MoveReportProjection;
import kg.kundoluk.school.model.MoveReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MoveReportRepository extends JpaRepository<MoveReport, Long> {

    @Query(value = "select sms.id, sms.quarter_id as quarterId, sms.class_id as classId, sms.dep_country_count as depCountryCount, sms.dep_region_count as depRegionCount, sms.dep_rayon_count as depRayonCount, sms.arr_country_count as arrCountryCount, sms.arr_region_count as arrRegionCount, sms.arr_rayon_count as arrrayonCount, sms.gender, sms.count, sms.initial_count as initialCount, sms.final_count as finalCount from student_move_statistics sms where sms.quarter_id = ?1 and (?2 = 0 or sms.class_id = ?2)",nativeQuery = true)
    List<MoveReportProjection> findAllByQuarterId(Long quarterId, Long classId);
}
