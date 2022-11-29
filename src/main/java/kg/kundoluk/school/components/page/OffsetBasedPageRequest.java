package kg.kundoluk.school.components.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
public class OffsetBasedPageRequest implements Pageable, Serializable {

    private Integer limit;
    private Integer offset;
    private Sort sort;

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(limit, limit + offset, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? getPrevious() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), sort);
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    private OffsetBasedPageRequest getPrevious() {
        return hasPrevious() ? new OffsetBasedPageRequest(offset - getPageSize(), getPageSize(), sort) : this;
    }
}
