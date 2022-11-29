package kg.kundoluk.school.components.page.impl;

import kg.kundoluk.school.components.annotations.Mapper;
import kg.kundoluk.school.components.page.OffsetBasedPageRequest;
import kg.kundoluk.school.components.page.PageBaleMapper;
import kg.kundoluk.school.components.page.PageRequestDto;
import kg.kundoluk.school.components.page.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Mapper
public class PageBaleMapperImpl<T> implements PageBaleMapper<T> {
    @Override
    public Pageable toPageable(PageRequestDto pageRequest) {
        return OffsetBasedPageRequest.builder()
                .limit(pageRequest.getLimit())
                .offset(pageRequest.getOffset())
                .sort(Sort.by(Sort.Direction.DESC, "id"))
                .build();
    }

    @Override
    public PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse(page);
    }
}
