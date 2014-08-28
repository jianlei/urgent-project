package com.daren.core.util.jpa.page;


import java.util.List;

/**
 * User: tangtao
 * Date: 13-9-12
 * Time: 下午11:05
 */
@SuppressWarnings("serial")
public class Pagination extends SimplePage implements java.io.Serializable,
        IPaginable {

    /**
     * 当前页的数据
     */
    @SuppressWarnings("unchecked")
    private List list;

    public Pagination() {
    }

    public Pagination(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

    @SuppressWarnings("unchecked")
    public Pagination(int pageNo, int pageSize, int totalCount, List list) {
        super(pageNo, pageSize, totalCount);
        this.list = list;
    }

    public int getFirstResult() {
        int pa =(this.pageNo - 1) * this.pageSize;
        return pa;
    }

    @SuppressWarnings("unchecked")
    public List getList() {
        return list;
    }

    @SuppressWarnings("unchecked")
    public void setList(List list) {
        this.list = list;
    }
}

