package com.daren.example.webapp.wicket.data;

import com.daren.admin.api.biz.IAreaBeanService;
import com.daren.admin.entities.AreaBean;
import com.daren.core.util.JNDIHelper;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:    区域数表提供
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 14:00
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 14:00
 * 修改备注:  [说明本次修改内容]
 */
public class AreaTreeProvider extends SortableTreeProvider<AreaBean, String> {

    private IAreaBeanService areaBeanService;

    private List<AreaBean> beanList;

    public AreaTreeProvider() {
        try {
            areaBeanService = JNDIHelper.getJNDIServiceForName(IAreaBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载根节点数据
     *
     * @return
     */
    public List<AreaBean> getData() {
        beanList = areaBeanService.getRootBeanList();
        return beanList;
    }

    @Override
    public Iterator<? extends AreaBean> getRoots() {
        getData();
        return beanList.iterator();
    }

    @Override
    public boolean hasChildren(AreaBean node) {
        //如果没有子节点，去数据库查询
        if (node.getChildList() == null) {
            node = (AreaBean) areaBeanService.getEntity(node.getId());
            if (node.getChildList() == null)
                return false;
        }
        return !node.getChildList().isEmpty();
    }

    @Override
    public Iterator<? extends AreaBean> getChildren(AreaBean node) {
        //如果没有子节点，去数据库查询
        if (node.getChildList() == null) {
            node = (AreaBean) areaBeanService.getEntity(node.getId());
        }
        return node.getChildList().iterator();
    }

    @Override
    public IModel<AreaBean> model(AreaBean object) {
        return Model.of(object);
    }
}
