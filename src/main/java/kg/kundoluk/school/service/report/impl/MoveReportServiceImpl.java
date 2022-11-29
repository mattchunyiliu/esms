package kg.kundoluk.school.service.report.impl;

import kg.kundoluk.school.dto.report.MoveReportDto;
import kg.kundoluk.school.dto.report.MoveReportProjection;
import kg.kundoluk.school.model.MoveReport;
import kg.kundoluk.school.repository.MoveReportRepository;
import kg.kundoluk.school.repository.QuarterRepository;
import kg.kundoluk.school.repository.SchoolClassRepository;
import kg.kundoluk.school.service.report.MoveReportService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MoveReportServiceImpl implements MoveReportService {
    private final MoveReportRepository moveReportRepository;
    private final QuarterRepository quarterRepository;
    private final SchoolClassRepository schoolClassRepository;

    public MoveReportServiceImpl(MoveReportRepository moveReportRepository, QuarterRepository quarterRepository, SchoolClassRepository schoolClassRepository) {
        this.moveReportRepository = moveReportRepository;
        this.quarterRepository = quarterRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public MoveReport create(MoveReportDto moveReportDto) {
        return moveReportRepository.save(
                new MoveReport()
                        .setSchoolClass(schoolClassRepository.getOne(moveReportDto.getClassId()))
                        .setQuarter(quarterRepository.getOne(moveReportDto.getQuarterId()))
                        .setGender(moveReportDto.getGender())
                        .setDepCountryCount(moveReportDto.getDepCountryCount())
                        .setDepRegionCount(moveReportDto.getDepRegionCount())
                        .setDepRayonCount(moveReportDto.getDepRayonCount())
                        .setArrCountryCount(moveReportDto.getArrCountryCount())
                        .setArrRegionCount(moveReportDto.getArrRegionCount())
                        .setArrRayonCount(moveReportDto.getArrRayonCount())
                        .setCount(moveReportDto.getCount())
                        .setInitialCount(moveReportDto.getInitialCount())
                        .setFinalCount(moveReportDto.getFinalCount())
        );
    }

    @Override
    public MoveReport update(MoveReport moveReport, MoveReportDto moveReportDto) {
        return moveReportRepository.save(
                moveReport
                        .setGender(moveReportDto.getGender())
                        .setDepCountryCount(moveReportDto.getDepCountryCount())
                        .setDepRegionCount(moveReportDto.getDepRegionCount())
                        .setDepRayonCount(moveReportDto.getDepRayonCount())
                        .setArrCountryCount(moveReportDto.getArrCountryCount())
                        .setArrRegionCount(moveReportDto.getArrRegionCount())
                        .setArrRayonCount(moveReportDto.getArrRayonCount())
                        .setCount(moveReportDto.getCount())
                        .setInitialCount(moveReportDto.getInitialCount())
                        .setFinalCount(moveReportDto.getFinalCount())
        );
    }

    @Override
    public void delete(Long id) {
        moveReportRepository.deleteById(id);
    }

    @Override
    public List<MoveReportProjection> findAllByQuarter(Long quarterId, Long classId) {
        if (classId == null)
            classId  = 0L;
        return moveReportRepository.findAllByQuarterId(quarterId, classId);
    }
}
