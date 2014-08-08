package com.daren.admin.webapp.wicket.data;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.entities.PermissionBean;
import com.daren.core.util.JNDIHelper;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 项目名称:  urgent-project
 * 类描述:   提供 权限数表的数据类
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 14:15
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 14:15
 * 修改备注:  [说明本次修改内容]
 */
public class PermissionTreeProvider extends SortableTreeProvider<PermissionBean, String> {

    private IPermissionBeanService permissionBeanService;
    private List<PermissionBean> beanList;

    public PermissionTreeProvider() {
        try {
            permissionBeanService = JNDIHelper.getJNDIServiceForName(IPermissionBeanService.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载根节点数据
     *
     * @return
     */
    public List<PermissionBean> getData() {
        beanList = permissionBeanService.getRootBeanList();
        return beanList;
    }

    @Override
    public Iterator<? extends PermissionBean> getRoots() {
        getData();
        return beanList.iterator();
    }

    @Override
    public boolean hasChildren(PermissionBean node) {
        //如果没有子节点，去数据库查询
        if (node.getChildList() == null) {
            node = (PermissionBean) permissionBeanService.getEntity(node.getId());
            if (node.getChildList() == null)
                return false;
        }
        return !node.getChildList().isEmpty();
    }

    @Override
    public Iterator<? extends PermissionBean> getChildren(PermissionBean node) {
        //如果没有子节点，去数据库查询
        if (node.getChildList() == null) {
            node = (PermissionBean) permissionBeanService.getEntity(node.getId());
        }
        return node.getChildList().iterator();
    }

    @Override
    public IModel<PermissionBean> model(PermissionBean object) {
        return Model.of(object);
    }

}
