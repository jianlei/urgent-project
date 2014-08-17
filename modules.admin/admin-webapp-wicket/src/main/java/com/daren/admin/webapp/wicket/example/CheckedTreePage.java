package com.daren.admin.webapp.wicket.example;

import com.daren.admin.entities.AreaBean;
import com.daren.admin.webapp.wicket.data.AreaTreeProvider;
import com.daren.core.web.component.table.SingleSelectNestedTree;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

/**
 * 项目名称:  urgent-project
 * 类描述:    test for tree select
 * 创建人:    sunlf
 * 创建时间:  2014/8/9 12:41
 * 修改人:    sunlf
 * 修改时间:  2014/8/9 12:41
 * 修改备注:  [说明本次修改内容]
 */
public class CheckedTreePage extends BasePanel {
    final SingleSelectNestedTree<AreaBean> tree;
    //数据模型，用于获得返回的选中节点
    Model<AreaBean> selModel = new Model<>(new AreaBean());

    public CheckedTreePage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        final JQueryFeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
        add(feedbackPanel.setOutputMarkupId(true));
        tree = new SingleSelectNestedTree<>("tree", new AreaTreeProvider(), selModel);
        add(tree);
        //测试按钮，用于显示获得返回的选中节点
        add(new AjaxLink("show") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript("alert('" + selModel.getObject().getName() + "')");
                System.out.print(selModel.getObject().getName());
            }
        });

    }
}
