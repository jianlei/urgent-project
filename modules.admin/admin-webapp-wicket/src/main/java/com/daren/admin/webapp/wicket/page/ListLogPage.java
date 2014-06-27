package com.daren.admin.webapp.wicket.page;

import com.daren.core.web.wicket.BasePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：
 * @创建人：sunlf
 * @创建时间：2014-03-28 下午11:14
 * @修改人：wkr
 * @修改时间：20140329
 * @修改备注：增加功能
 */

public class ListLogPage extends BasePage {
    public ListLogPage() {

        //填充数据列表
        List<LogBean> logBeanList = getAllEntity();
        ListDataProvider<LogBean> listDataProvider = new ListDataProvider<LogBean>(logBeanList);


        add(new DataView<LogBean>("logRows", listDataProvider) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<LogBean> item) {
                final LogBean logBean;
                logBean = item.getModelObject();


                item.add(new Label("content", logBean.getContent()));
                item.add(new Label("module", logBean.getModule()));
                item.add(new Label("level", logBean.getLevel()));
                item.add(new Label("createDate", logBean.getCreateDate()));
                item.add(new Link("del") {
                    @Override
                    public void onClick() {
//                        setResponsePage(ListLogPage.class);
                    }
                });

                item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        });
    }

    private List<LogBean> getAllEntity() {
        List<LogBean> beanList = new ArrayList<LogBean>();
        for (int i = 1; i < 9; i++) {
            LogBean bean = new LogBean();
            bean.setCreateDate("2014-03-0" + i);
            bean.setContent("这是一个测试日志的内容" + i);
            bean.setModule("管理模块" + i);
            int ii = (i / 2);
            bean.setLevel(ii + "");
            beanList.add(bean);
        }
        return beanList;
    }

    class LogBean implements Serializable {
        String createDate; //创建日期
        String content; //内容
        String module;  //所属模块
        String level;   //级别

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
