package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.shiftTime.ShiftTimeCreateRequest;
import kg.kundoluk.school.dto.shiftTime.ShiftTimeRequest;
import kg.kundoluk.school.model.school.ShiftTime;

import java.util.List;

public interface ShiftTimeEndpoint {
    ShiftTime get(Long id);
    void delete(Long id);
    ShiftTime create(ShiftTimeCreateRequest shiftTimeCreateRequest);
    void createBulk(List<ShiftTimeCreateRequest> shiftTimeCreateRequests);
    ShiftTime edit(ShiftTimeCreateRequest shiftTimeCreateRequest, ShiftTime shiftTime);
    List<ShiftTime> listByShift(Long shiftId);
    List<ShiftTimeCreateRequest> listBySchool(Long schoolId);
}
