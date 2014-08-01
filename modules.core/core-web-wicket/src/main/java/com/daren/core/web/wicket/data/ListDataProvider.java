package com.daren.core.web.wicket.data;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:   重新构造的类，防止getData调用多次
 * 创建人:    sunlf
 * 创建时间:  2014/7/31 23:47
 * 修改人:    sunlf
 * 修改时间:  2014/7/31 23:47
 * 修改备注:  [说明本次修改内容]
 */
public class ListDataProvider<T extends Serializable> implements IDataProvider<T> {
    private static final long serialVersionUID = 1L;

    /**
     * reference to the list used as dataprovider for the dataview
     */
    private List<T> list;

    /**
     * Constructs an empty provider. Useful for lazy loading together with {@linkplain #getData()}
     */
    public ListDataProvider() {
        this(Collections.<T>emptyList());
    }

    /**
     * @param list the list used as dataprovider for the dataview
     */
    public ListDataProvider(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("argument [list] cannot be null");
        }

        this.list = list;
    }

    /**
     * Subclass to lazy load the list
     *
     * @return The list
     */
    protected List<T> getData() {
        return list;
    }

    @Override
    public Iterator<? extends T> iterator(final long first, final long count) {

        long toIndex = first + count;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        Iterator<? extends T> result = list.subList((int) first, (int) toIndex).listIterator();
//        list.clear();
        return result;
    }

    /**
     * @see IDataProvider#size()
     */
    @Override
    public long size() {
        if (list.size() == 0) {
            list = getData();
        }
        return list.size();
    }

    /**
     * @see IDataProvider#model(Object)
     */
    @Override
    public IModel<T> model(T object) {
        return new Model<T>(object);
    }

    /**
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
    }
}
