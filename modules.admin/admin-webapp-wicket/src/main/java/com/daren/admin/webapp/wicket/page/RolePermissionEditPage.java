package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IPermissionBeanService;
import com.daren.admin.entities.PermissionBean;
import com.daren.admin.entities.RoleBean;
import com.daren.admin.webapp.wicket.data.PermissionTreeProvider;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisIndicatingAjaxLink;
import com.daren.core.web.component.table.AutocheckedFolder;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.SetModel;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * 项目名称:  urgent-project
 * 类描述:    通过角色编辑权限的数表页面类
 * 创建人:    sunlf
 * 创建时间:  2014/10/4 12:41
 * 修改人:    sunlf
 * 修改时间:  2014/10/4 12:41
 * 修改备注:  [说明本次修改内容]
 */
public class RolePermissionEditPage extends Panel {
    @Inject
    private IPermissionBeanService permissionBeanService;
    private RoleBean roleBean;

    public RolePermissionEditPage(String id, Model<RoleBean> model) {
        super(id, model);
        roleBean=model.getObject();
        add(new Label("name",roleBean.getName()));
        final JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback") ;
        add(feedbackPanel.setOutputMarkupId(true));

        final Set<PermissionBean> checkedNodes = new HashSet<PermissionBean>(roleBean.getPermissionList());
//        记录选中的节点
        final SetModel<PermissionBean> checkedNodesModel = new SetModel<PermissionBean>(checkedNodes);
        final DefaultNestedTree<PermissionBean> tree = new DefaultNestedTree<PermissionBean>("tree", new PermissionTreeProvider()) {
            /**
             * To use a custom component for the representation of a node's content we would
             * override this method.
             */
            @Override
            protected Component newContentComponent(String id, IModel<PermissionBean> node) {
                this.expand(node.getObject());
                return new AutocheckedFolder<PermissionBean>(id, this, node, checkedNodesModel) {
                };
            } ;
        };
        /*tree.add(new AjaxEventBehavior("click") {

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                for (PermissionBean node : checkedNodes) {
                    feedbackPanel.info(node.getName());
                }
                target.add(feedbackPanel);
            }
        });*/
        add(tree);

        add(new IrisIndicatingAjaxLink("save") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(checkedNodes.size()==0){
                    feedbackPanel.error("权限不能为空！");
                    target.add(feedbackPanel);
                    return;
                }
                permissionBeanService.assignPermission(roleBean,checkedNodes);
                feedbackPanel.info("权限分配成功！");
                target.add(feedbackPanel);
            }
        });

        add(new IrisIndicatingAjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                onDeleteTabs(target);
            }
        });

    }

    // Hook 回调函数
    protected void onDeleteTabs(AjaxRequestTarget target) {
    }


}
