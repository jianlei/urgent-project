package com.daren.admin.webapp.wicket.page;

import com.daren.admin.api.biz.IDictBeanService;
import com.daren.admin.entities.DictBean;
import com.daren.core.web.component.extensions.ajax.markup.html.IrisDeleteAjaxLink;
import com.daren.core.web.component.table.IrisDataTable;
import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @类描述：字典列表页面
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午10:46
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class DictListPage extends BasePanel {

    private final static int numPerPage = 10;
    private final static String CONST_LIST = "字典管理";
    private final static String CONST_ADD = "添加字典";
    private final static String CONST_EDIT = "编辑字典";

    private final RepeatingView repeatingView = new RepeatingView("repeatingView");
    DictDataProvider provider = new DictDataProvider();
    //注入字典业务服务
    @Inject
    @Reference(id = "dictBeanService", serviceInterface = IDictBeanService.class)
    private IDictBeanService dictBeanService;

    //构造函数
    public DictListPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        super.tabTitle=CONST_LIST;  //主tab的标题
     }

    @Override
    public Fragment createMainFragment(String id, String markupId) {
        return new MainFragment(id,markupId);
    }


    /**
     * 初始化新增按钮
     *
     * @return
     */
    private AjaxButton initAddButton(Form form) {
        //新增
        AjaxButton addButton = new AjaxButton("add", form) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_ADD, null);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                createNewTab(target, CONST_ADD, null);

            }
        };
        return addButton;
    }

    /**
     * 创建新的页面，用于新增和修改
     *
     * @param target
     * @param newTabType "修改字典"||"新增字典"
     * @param row        数据
     */
    private void createNewTab(AjaxRequestTarget target, final String newTabType, final DictBean row) {
        //去掉第二个tab
        if (tabPanel.getModelObject().size() == 2) {
            tabPanel.getModelObject().remove(1);
        }
        tabPanel.add(new AjaxTab(Model.of(newTabType)) {
            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getLazyPanel(String panelId) {
                //通过repeatingView增加新的panel
                repeatingView.removeAll();
                DictAddPage dictAddPage = new DictAddPage(repeatingView.newChildId(), newTabType, Model.of(row)) {
                    //关闭新增tab
                    @Override
                    protected void onDeleteTabs(AjaxRequestTarget target) {
                        if (tabPanel.getModelObject().size() == 2)
                            tabPanel.getModelObject().remove(1);
                        target.add(tabPanel);
                    }
                };
                repeatingView.add(dictAddPage);
                Fragment fragment = new Fragment(panelId, "addPanel", DictListPage.this);
                fragment.add(repeatingView);
                return fragment;
            }
        });

        tabPanel.setActiveTab(1);
        target.add(tabPanel);
    }

    //列表显示
    public class MainFragment extends Fragment {
        private final JQueryFeedbackPanel feedbackPanel;
        private final WebMarkupContainer container;

        public MainFragment(String id, String markupId) {
            super(id, markupId, DictListPage.this);

            container = new WebMarkupContainer("container");
            add(container.setOutputMarkupId(true));
            //add feedback
            feedbackPanel = new JQueryFeedbackPanel("feedback");
            container.add(feedbackPanel.setOutputMarkupId(true));
            //add table
            /*final WebMarkupContainer table = new WebMarkupContainer("table");
            container.add(table.setOutputMarkupId(true));*/

            //add dataTable
            final IrisDataTable<DictBean,String> dataTable = new IrisDataTable<DictBean,String>("table",initColumns(), provider, numPerPage) {
                private static final long serialVersionUID = 1L;


                /*@Override
                protected void populateItem(Item<DictBean> item) {
                    final DictBean row = item.getModelObject();


                    //add delete button
                    item.add(initDeleteButton(row));
                    //add update button
                    item.add(initEditButton(row));
                }*/
            };
            container.add(dataTable);

            /*//增加分页指示器
            IrisAjaxNavigationToolbar pagingNavigator = new IrisAjaxNavigationToolbar("navigator",table) {
            };
            table.add(pagingNavigator);*/

            //增加form
            Form<DictBean> dictForm = new Form<>("form", new CompoundPropertyModel<>(new DictBean()));
            TextField textField = new TextField("type");
            textField.setRequired(false);
            dictForm.add(textField.setOutputMarkupId(true));

            //find button
            dictForm.add(initFindButton(provider, dictForm));
            //add button
            dictForm.add(initAddButton(dictForm));

            add(dictForm);
        }

         

        private List<? extends IColumn<DictBean, String>> initColumns() {
            List<IColumn<DictBean, String>> columns = new ArrayList<IColumn<DictBean, String>>();
            columns.add(new PropertyColumn<DictBean, String>(Model.of("标签名"), "label"));
            columns.add(new PropertyColumn<DictBean, String>(Model.of("数据值"), "value"));
            columns.add(new PropertyColumn<DictBean, String>(Model.of("类型"), "type"));
            columns.add(new PropertyColumn<DictBean, String>(Model.of("描述"), "description"));
            columns.add(new PropertyColumn<DictBean, String>(Model.of("排序"), "sort"));
            return columns;

        }

        /**
         * 初始化编辑按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initEditButton(final DictBean row) {
            //修改功能
            AjaxLink alink = new AjaxLink("edit") {
                @Override
                public void onClick(AjaxRequestTarget target) {
                    createNewTab(target, CONST_EDIT, row);
                }
            };
            return alink;
        }

        /**
         * 初始化删除按钮
         *
         * @param row 数据
         * @return link
         */
        private AjaxLink initDeleteButton(final DictBean row) {
            //删除功能
            IrisDeleteAjaxLink alink = new IrisDeleteAjaxLink("delete") {
               @Override
                public void onClick(AjaxRequestTarget target) {
                    try {
                        dictBeanService.deleteEntity(row.getId());
                        feedbackPanel.info("删除成功！");
                        target.addChildren(getPage(), JQueryFeedbackPanel.class);
                        target.add(container);
                    } catch (Exception e) {
                        feedbackPanel.error("删除失败！");
                        e.printStackTrace();
                    }
                }
            };
            return alink;
        }

        /**
         * 初始化查询按钮
         *
         * @param provider
         * @param form
         * @return
         */
        private AjaxButton initFindButton(final DictDataProvider provider, Form form) {

            return new AjaxButton("find", form) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    DictBean dictBean = (DictBean) form.getModelObject();
                    provider.setDictBean(dictBean);
                    target.add(container);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    target.add(feedbackPanel);
                }
            };
        }
    }

    /**
     * //查询数据提供者
     */
    class DictDataProvider extends SortableDataProvider {
        List<DictBean> newList;
        private DictBean dictBean = null;

        public void setDictBean(DictBean dictBean) {
            this.dictBean = dictBean;
        }


        protected List<DictBean> getData() {
            //类型为空时候，返回全部记录
            if (dictBean == null || dictBean.getType().equals("")){
                newList=dictBeanService.getAllEntity();
            }

            else {
                 newList=dictBeanService.query(dictBean);
            }
            return newList;
        }

        @Override
        public Iterator iterator(long first, long count) {

            getData();
            // Sort the data
            int _first=new Long(first).intValue();
            int _count=new Long(count).intValue();

            // Return the data for the current page - this can be determined only after sorting
            return newList.subList(_first, _first +_count).iterator();
        }

        @Override
        public long size() {
            getData();
            return newList.size();
        }

        @Override
        public IModel model(final Object object) {
            return new AbstractReadOnlyModel<DictBean>() {
                @Override
                public DictBean getObject() {
                    return (DictBean) object;
                }
            };
        }
    }
}
