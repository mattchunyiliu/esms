package kg.kundoluk.school.endpoint;

import kg.kundoluk.school.dto.shift.ShiftCreateRequest;
import kg.kundoluk.school.model.school.Shift;

import java.util.List;

public interface ShiftEndpoint {
    Shift get(Long id);
    void delete(Long id);
    Shift create(ShiftCreateRequest shiftCreateRequest);
    Shift edit(ShiftCreateRequest shiftCreateRequest, Shift shift);
    List<Shift> listBySchool(Long schoolId);
}
