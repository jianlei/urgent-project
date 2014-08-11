package com.daren.admin.webapp.wicket.example;

import com.daren.admin.entities.AreaBean;
import com.daren.admin.webapp.wicket.data.AreaTreeProvider;
import com.daren.admin.webapp.wicket.table.AutocheckedFolder;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.SetModel;

import java.util.HashSet;
import java.util.Set;

/**
 * 项目名称:  urgent-project
 * 类描述:    test for tree select
 * 创建人:    sunlf
 * 创建时间:  2014/8/9 12:41
 * 修改人:    sunlf
 * 修改时间:  2014/8/9 12:41
 * 修改备注:  [说明本次修改内容]
 */
public class PageTest extends BasePanel {
    private Behavior theme;

    public PageTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel.setOutputMarkupId(true));
        theme = new WindowsTheme();

        final Set<AreaBean> checkedNodes = new HashSet<AreaBean>();
//        记录选中的节点
        final SetModel<AreaBean> checkedNodesModel = new SetModel<AreaBean>(
                checkedNodes);
        final DefaultNestedTree<AreaBean> tree = new DefaultNestedTree<AreaBean>("tree", new AreaTreeProvider()) {

            /**
             * To use a custom component for the representation of a node's content we would
             * override this method.
             */
            @Override
            protected Component newContentComponent(String id, IModel<AreaBean> node) {
                return new AutocheckedFolder<AreaBean>(id, this, node, checkedNodesModel) {
                   /* @Override
                    protected Component newCheckBox(String id, IModel<AreaBean> model) {
                        return super.newCheckBox(id, model);
                    }*/

                    /*@Override
                    protected void onUpdate(AjaxRequestTarget target) {
                        feedbackPanel.info(this.getModelObject().getName());
                        super.onUpdate(target);
                        target.add(feedbackPanel);
                    }*/

                    /* @Override
                    protected boolean isSelected() {
                        return false;
                    }

                    @Override
                    protected void onClick(AjaxRequestTarget target) {
                        this.setOutputMarkupId(true);
                        this.add(new AttributeModifier("class",getSelectedStyleClass()));
                        super.onClick(target);
                         target.add(this);

                    }

                    @Override
                    protected boolean isClickable() {
                        return true;
                    }*/

                };
            }

            ;
        };
        tree.add(new AjaxEventBehavior("click") {

            @Override
            protected void onEvent(AjaxRequestTarget target) {
                for (AreaBean node : checkedNodes) {
                    feedbackPanel.info(node.getName());
                }
                target.add(feedbackPanel);
            }
        });
        add(tree);
        add(new AjaxLink("show") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                for (AreaBean node : checkedNodes) {
                    feedbackPanel.info(node.getName());
                }
                target.add(feedbackPanel);
            }
        });

    }
}
