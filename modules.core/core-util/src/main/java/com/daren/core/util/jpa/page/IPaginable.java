package com.daren.core.util.jpa.page;

/**
 * User: tangtao
 * Date: 13-9-12
 * Time: 下午11:02
 */
/**
 * 可分页接口
 *
 * @author liufang
 *
 */
public interface IPaginable<T> {
    /**
     * 总记录数
     *
     * @return
     */
    public int getTotalCount();

    /**
     * 总页数
     *
     * @return
     */
    public int getTotalPage();

    /**
     * 每页记录数
     *
     * @return
     */
    public int getPageSize();

    /**
     * 当前页号
     *
     * @return
     */
    public int getPageNo();

    /**
     * 是否第一页
     *
     * @return
     */
    public boolean isFirstPage();

    /**
     * 是否最后一页
     *
     * @return
     */
    public boolean isLastPage();

    /**
     * 返回下页的页号
     */
    public int getNextPage();

    /**
     * 返回上页的页号
     */
    public int getPrePage();
}

