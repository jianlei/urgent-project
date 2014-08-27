package com.daren.example.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OfficeTest extends BasePanel {
    WindowOfficePage dialog;
    final WebMarkupContainer dialogWrapper;
    public OfficeTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        dialogWrapper = new WebMarkupContainer("dialogWrapper") {
            @Override
            protected void onBeforeRender() {
                if (dialog == null) {
                    addOrReplace(new Label("dialog"));
                } else {
                    addOrReplace(dialog);
                }
                super.onBeforeRender();
            }
        };
        this.add(dialogWrapper.setOutputMarkupId(true));
        this.add(initGisButton());
    }

    private void createDialog(AjaxRequestTarget target, final String title) {
        if (dialog != null) {
            dialogWrapper.removeAll();
        }
        dialog = new WindowOfficePage("dialog", title) {
            @Override
            public void updateTarget(AjaxRequestTarget target) {

            }
        };
        target.add(dialogWrapper);
        dialog.open(target);
    }

    private AjaxLink initGisButton() {
        AjaxLink alink = new AjaxLink("office") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                createDialog(target, "标注地址");
            }
        };
        return alink;
    }
}
