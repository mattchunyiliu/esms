package kg.kundoluk.school.dto.projection;

import java.io.Serializable;

public interface SchoolClassDTO extends SchoolClassBaseDTO{
    Long getLanguageId();
    Long getShiftId();
    Long getPersonId();
    String getPersonTitle();
}
