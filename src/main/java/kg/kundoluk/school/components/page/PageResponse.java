package kg.kundoluk.school.components.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> list;
    private Long totalCount;

    public PageResponse(Page<T> page) {
        this.list = page.getContent();
        this.totalCount = page.getTotalElements();
    }
}
