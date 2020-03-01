package io.lite.framework.common;


import lombok.Data;

import java.util.List;

@Data
public class Page<T> extends VO {

    private static final long serialVersionUID = 1L;

    private List<T> list;
    private Long    totalElements;
    private int     totalPages;
    private int     size;
    private int     number;


    public static Page fromPage(org.springframework.data.domain.Page p) {
        Page page = new Page();
        page.list = p.getContent();
        page.totalElements = p.getTotalElements();
        page.totalPages = p.getTotalPages();
        page.size = p.getSize();
        page.number = p.getNumber();
        return page;
    }
}
