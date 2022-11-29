package kg.kundoluk.school.endpoint.impl;

import kg.kundoluk.school.components.annotations.Endpoint;
import kg.kundoluk.school.dto.quarter.QuarterCreateRequest;
import kg.kundoluk.school.dto.quarter.QuarterDto;
import kg.kundoluk.school.dto.quarter.QuarterRequest;
import kg.kundoluk.school.dto.quarter.QuarterShortResponse;
import kg.kundoluk.school.endpoint.QuarterEndpoint;
import kg.kundoluk.school.model.school.Quarter;
import kg.kundoluk.school.repository.ChronicleRepository;
import kg.kundoluk.school.repository.SchoolRepository;
import kg.kundoluk.school.service.school.QuarterService;
import kg.kundoluk.school.utils.TimeHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Endpoint
public class QuarterEndpointImpl implements QuarterEndpoint {
    private final QuarterService quarterService;
    private final SchoolRepository schoolRepository;
    private final ChronicleRepository chronicleRepository;

    public QuarterEndpointImpl(QuarterService quarterService, SchoolRepository schoolRepository, ChronicleRepository chronicleRepository) {
        this.quarterService = quarterService;
        this.schoolRepository = schoolRepository;
        this.chronicleRepository = chronicleRepository;
    }

    @Override
    public Quarter create(QuarterCreateRequest quarterCreateRequest) {
        return quarterService.create(toQuarterRequest(quarterCreateRequest));
    }

    @Override
    public void createBulk(QuarterDto quarterDto) {
        for (QuarterShortResponse quarterShortResponse: quarterDto.getQuarters()){
            QuarterCreateRequest quarterCreateRequest = new QuarterCreateRequest(quarterDto.getChronicleId(), quarterDto.getSchoolId(), quarterShortResponse.getStartDate(), quarterShortResponse.getEndDate(), quarterShortResponse.getStatus(), quarterShortResponse.getQuarter());

            if (!Objects.isNull(quarterShortResponse.getId())){
                Quarter quarter = quarterService.get(quarterShortResponse.getId());
                edit(quarterCreateRequest, quarter);
            } else {
                create(quarterCreateRequest);
            }
        }
    }

    @Override
    public Quarter edit(QuarterCreateRequest quarterCreateRequest, Quarter quarter) {
        return quarterService.edit(QuarterRequest
                .builder()
                .title(quarterCreateRequest.getQuarter())
                .status(quarterCreateRequest.getStatus())
                .startDate(LocalDate.parse(quarterCreateRequest.getStartDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .endDate(LocalDate.parse(quarterCreateRequest.getEndDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .build(), quarter);
    }

    @Override
    public QuarterShortResponse get(Long id) {
        return toQuarterShortResponse(quarterService.get(id));
    }

    @Override
    public void delete(Long id) {
        quarterService.delete(id);
    }

    @Override
    public List<QuarterShortResponse> listBySchoolChronicle(Long schoolId, Long chronicleId) {
        return quarterService.listBySchoolChronicle(schoolId, chronicleId).stream().map(this::toQuarterShortResponse).collect(Collectors.toList());
    }

    private QuarterRequest toQuarterRequest(QuarterCreateRequest quarterCreateRequest) {
        return QuarterRequest
                .builder()
                .title(quarterCreateRequest.getQuarter())
                .chronicleYear(chronicleRepository.getOne(quarterCreateRequest.getChronicleId()))
                .startDate(LocalDate.parse(quarterCreateRequest.getStartDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .endDate(LocalDate.parse(quarterCreateRequest.getEndDate(), TimeHelper.DATE_REVERSE_FORMATTER))
                .school(schoolRepository.getOne(quarterCreateRequest.getSchoolId()))
                .status(quarterCreateRequest.getStatus())
                .build();
    }

    private QuarterShortResponse toQuarterShortResponse(Quarter quarter){
        QuarterShortResponse quarterShortResponse = new QuarterShortResponse();
        quarterShortResponse.setId(quarter.getId());
        quarterShortResponse.setStartDate(TimeHelper.DATE_REVERSE_FORMATTER.format(quarter.getStartDate()));
        quarterShortResponse.setEndDate(TimeHelper.DATE_REVERSE_FORMATTER.format(quarter.getEndDate()));
        quarterShortResponse.setQuarter(quarter.getQuarter());
        quarterShortResponse.setStatus(quarter.getStatus());
        return quarterShortResponse;
    }
}
