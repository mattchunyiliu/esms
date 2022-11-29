package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.quarter.QuarterCreateRequest;
import kg.kundoluk.school.dto.quarter.QuarterDto;
import kg.kundoluk.school.dto.quarter.QuarterShortResponse;
import kg.kundoluk.school.model.school.Quarter;

import java.util.List;

public interface QuarterEndpoint {
    Quarter create(QuarterCreateRequest quarterCreateRequest);

    void createBulk(QuarterDto quarterDto);

    Quarter edit(QuarterCreateRequest quarterCreateRequest, Quarter quarter);

    QuarterShortResponse get(Long id);

    void delete(Long id);

    List<QuarterShortResponse> listBySchoolChronicle(Long schoolId, Long chronicleId);

}
