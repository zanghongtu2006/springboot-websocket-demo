package com.hongtu.project.im.commom;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午5:09 19-4-24
 */
@Data
public class PageResponse<T> {
    /**
     * 当前页
     */
    private int page;

    /**
     * 每页个数
     */
    private int pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 结果列表
     */
    private List<T> rows;

    public PageResponse() {
    }

    public PageResponse(int page, int pageSize, long total, List<T> rows) {
        this.page = page + 1;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = rows;
    }

    public PageResponse(int page, int pageSize, long total) {
        this.page = page + 1;
        this.pageSize = pageSize;
        this.total = total;
        this.rows = new LinkedList<>();
    }
}
