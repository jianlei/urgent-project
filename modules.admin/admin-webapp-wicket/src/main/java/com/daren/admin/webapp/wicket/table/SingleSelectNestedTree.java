package com.daren.admin.webapp.wicket.table;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.SetModel;

/**
 * 项目名称:  urgent-project
 * 类描述:    单选的树控件,用于弹出时，供用户进行单选
 * 创建人:    sunlf
 * 创建时间:  2014/8/10 20:10
 * 修改人:    sunlf
 * 修改时间:  2014/8/10 20:10
 * 修改备注:  [说明本次修改内容]
 */
public class SingleSelectNestedTree<T> extends DefaultNestedTree<T> {
    private static final long serialVersionUID = 1L;
    SetModel<T> state = new SetModel<>();
    //存储被选中的模型
    private IModel<T> selectedModel;

    public SingleSelectNestedTree(String id, ITreeProvider<T> provider, IModel<T> selected) {
        super(id, provider);
        this.selectedModel = selected;

        this.setOutputMarkupId(true);
    }

    @Override
    protected Component newContentComponent(String id, IModel<T> node) {
        return new Folder<T>(id, SingleSelectNestedTree.this, node) {

            @Override
            protected void onInitialize() {
                super.onInitialize();
                SingleSelectNestedTree.this.expand(getModelObject());
            }

            /**
             * 无论是否有子节点，都可以点击
             * @return
             */
            @Override
            protected boolean isClickable() {

                return true;
            }

            /**
             * 点击后，记录点击的对象
             * @param target
             */
            @Override
            protected void onClick(AjaxRequestTarget target) {
                T t = getModelObject();
                selectedModel.setObject(t);
                target.add(SingleSelectNestedTree.this);
//                updateNode(t,target);
            }

            /**
             * 渲染选中节点的样式
             * @return
             */
            @Override
            protected boolean isSelected() {
                T t = getModelObject();
                {
                    if (t.equals(selectedModel.getObject()))
                        return true;
                    else
                        return false;
                }
            }
        };
    }
}
