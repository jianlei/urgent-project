package com.daren.apply.webapp.wicket.util;

import com.daren.attachment.entities.AttachmentBean;
import com.daren.core.api.IConst;
import com.daren.core.util.DateUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Comment;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.util.file.Files;

import javax.inject.Inject;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Administrator on 2014/9/26.
 */
public class PageUtil {
    //列表中附件预览按钮
    public static AjaxLink initPreviewButton(final AttachmentBean attachmentBean) {
        AjaxLink alink = new AjaxLink("previewDuplicate") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                FileInputStream fileInputStream = null;
                try {
                    File tempFile = new File(IConst.OFFICE_WEB_PATH_TEMP + attachmentBean.getName());
                    fileInputStream = new FileInputStream(attachmentBean.getPath());
                    DataInputStream data = new DataInputStream(fileInputStream);
                    Files.writeTo(tempFile, data);
                    fileInputStream.close();
//                    createDialog(target, "Office", IConst.OFFICE_WEB_PATH_READ + attachmentBean.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return alink;
    }
    //初始化流程历史列表
    public static WebMarkupContainer initHistoricProcessView(String historyProcessId, final TaskService taskService, final HistoryService historyService) {
        final WebMarkupContainer table = new WebMarkupContainer("historicTable");
        final DataView<HistoricActivityInstance> listView = new DataView<HistoricActivityInstance>("historicRows", new HistoricActivityInstanceProvider(historyProcessId, historyService), 10) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(Item<HistoricActivityInstance> item) {
                final HistoricActivityInstance historicActivityInstance = item.getModelObject();
                item.add(new Label("id", historicActivityInstance.getId()));
                item.add(new Label("name", historicActivityInstance.getActivityName()));
                item.add(new Label("assignee", historicActivityInstance.getAssignee()));
                item.add(new Label("startTime", DateUtil.convertDateToString(historicActivityInstance.getStartTime(), DateUtil.longSdf)));
                item.add(new Label("endTime", DateUtil.convertDateToString(historicActivityInstance.getEndTime(), DateUtil.longSdf)));
                List<Comment> commentList= taskService.getTaskComments(historicActivityInstance.getTaskId());
                String str="";
                for(Comment comment:commentList){
                    str=comment.getFullMessage()+str+" ";
                }
                item.add(new Label("comment", str));
            }
        };
        table.add(listView);
        return table;
    }

    //流程历史列表数据
    static class HistoricActivityInstanceProvider extends ListDataProvider {
        String historyProcessId;
        HistoryService historyService;
        public HistoricActivityInstanceProvider(String historyProcessId, HistoryService historyService) {
            this.historyProcessId = historyProcessId;
            this.historyService = historyService;
        }
        @Override
        protected List<HistoricActivityInstance> getData() {
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historyProcessId).list();
            for (int i = list.size(); i > 0; i--) {
                if (null == list.get(i - 1).getTaskId()) {
                    list.remove(i - 1);
                }
            }
            return list;
        }
    }
}
