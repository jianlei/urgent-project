package com.daren.example.webapp.wicket.page;

import com.daren.core.web.wicket.BasePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.http.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @类描述：测试标准ajax调用方式和wicket交互的例子
 * @创建人： sunlingfeng
 * @创建时间：2014/8/28
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class AjaxTest extends BasePanel{
    private final Label drawingCanvas;
    private final CanvasAjaxBejavior canvasAjaxBejavior;

    /**
     * 初始化页面
     * @param id
     * @param wmc
     */
    public AjaxTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
        final JQueryFeedbackPanel feedbackPanel=new JQueryFeedbackPanel("feedback");
        add(feedbackPanel);

        drawingCanvas = new Label("drawingCanvas","click me");
        drawingCanvas.setMarkupId("canvas");
        canvasAjaxBejavior = new CanvasAjaxBejavior();
        add(canvasAjaxBejavior);
        add(drawingCanvas);
    }

    @Override
    protected void onBeforeRender() {
        String callbackUrl = canvasAjaxBejavior
                .getCallbackUrl().toString();
        drawingCanvas.add(
                new AttributeModifier(
                        "my:canvas.callback",
                        callbackUrl));
        super.onBeforeRender();
        //called before the component is rendered
    }

    /**
     * 设置返回json
     * @param requestCycle
     */
    private void sendResponse(final RequestCycle requestCycle) {
        requestCycle.scheduleRequestHandlerAfterCurrent(
                new TextRequestHandler("application/json",
                        "UTF-8", "[5, 4, 3, 2, 1]"));
    }

    /**
     * 获得client请求的json数据
     * @param requestCycle
     */
    private void readRequestData(final RequestCycle requestCycle) {
        WebRequest wr=(WebRequest)requestCycle.getRequest();
        HttpServletRequest hsr=
                (HttpServletRequest) wr.getContainerRequest();
        try {
            BufferedReader br = hsr.getReader();

            String  jsonString = br.readLine();
            if((jsonString==null) || jsonString.isEmpty()){
                System.out.println(" no json found");
            }
            else {
                System.out.println(" json  is :"+ jsonString);
            }
            br.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 自定义ajax行为
     */
    private final class CanvasAjaxBejavior extends AbstractAjaxBehavior {
        private static final long serialVersionUID = 1L;
        @Override
        public void onRequest() {
            RequestCycle requestCycle = RequestCycle.get();
            readRequestData(requestCycle);
            sendResponse(requestCycle);
        }

        @Override
        protected void onComponentTag(final ComponentTag tag) {
            tag.put("my:canvas.callback",
                    getCallbackUrl().toString());
        }
        //the canvas ajax behavior
    }
}
