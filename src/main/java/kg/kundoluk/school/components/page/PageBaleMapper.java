package kg.kundoluk.school.components.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageBaleMapper<T> {
    Pageable toPageable(PageRequestDto pageRequest);
    PageResponse<T> toPageResponse(Page<T> page);
}
