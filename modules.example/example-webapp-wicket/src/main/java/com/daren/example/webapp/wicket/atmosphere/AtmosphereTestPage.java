package com.daren.example.webapp.wicket.atmosphere;

import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.atmosphere.EventBus;
import org.apache.wicket.atmosphere.Subscribe;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import java.util.Date;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/9/20
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AtmosphereTestPage extends BasePanel{
    private static final long serialVersionUID = 1L;

    private Component timeLabel;
    private Component messageLabel;
    private TextField<String> receiver;
    private TextField<String> input;
    public AtmosphereTestPage(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        add(timeLabel = new Label("time", Model.of("start")).setOutputMarkupId(true));
        add(messageLabel = new Label("message", Model.of("-")).setOutputMarkupId(true));

        Form<Void> form = new Form<Void>("form");
        add(form);
        form.add(receiver = new TextField<String>("receiver", Model.of("")));
        form.add(input = new TextField<String>("input", Model.of("")));
        form.add(new AjaxSubmitLink("send", form)
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                EventBus.get().post(
                        new ChatMessage(receiver.getModelObject(), input.getModelObject()));
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form)
            {
            }
        });

        setVersioned(false);
    }

    @Subscribe
    public void updateTime(AjaxRequestTarget target, Date event)
    {
        timeLabel.setDefaultModelObject(event.toString());
        target.add(timeLabel);
    }

    @Subscribe(contextAwareFilter = ReceiverFilter.class)
    public void receiveMessage(AjaxRequestTarget target, ChatMessage message)
    {
        messageLabel.setDefaultModelObject(message.getMessage());
        target.add(messageLabel);
    }
}
