package io.lite.framework.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PageRequest extends Request {

    private static final long serialVersionUID = 1L;

    public static final int MAX_ITEMS = 200;

    private Integer page = 0;
    private Integer size = MAX_ITEMS;

    private String sort;
    private String direction;

    public void copyPage(PageRequest request) {
        this.page = request.getPage();
        this.size = request.getSize();
        this.sort = request.getSort();
        this.direction = request.getDirection();
    }

    public boolean isPageable() {
        return page != null && size != null;
    }

    public boolean isSortable() {
        return sort != null;
    }

    public int getPageSize() {
        return size;
    }

    public int getPageNumber() {
        return page;
    }

    public long getOffset() {
        return (long) page * (long) size;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public Pageable toPageable() {
        if (isSortable()) {
            return org.springframework.data.domain.PageRequest.of(page, size, toSort());
        }

        return org.springframework.data.domain.PageRequest.of(page, size);
    }

    public Sort toSort() {
        if (isSortable()) {
            if (direction != null) {
                return Sort.by(Sort.Direction.fromString(direction), sort);
            }
            return Sort.by(sort);
        }

        return Sort.unsorted();
    }
}
