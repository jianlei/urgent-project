package com.daren.cooperate.core.util;

import com.tencent.xinge.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TengXunXinGe {
/*	private static long access_id=2100035597;
	private static String secret_key="07daab39e65c65b6dc575c0c0f7fc69a";
	private String token="cd623a0e72a6736444dd90dc38e9ff1ec49c4f6f";*/
	private static long access_id=2100047027;
	private static String secret_key="ce007ea5ddb662cc941414dbd334997a";
	//private static String token="e7853ef3f9b52fb24904b6641fd535628f0354df";//zfl
	private static String token="cd623a0e72a6736444dd90dc38e9ff1ec49c4f6f";//海信
	private static Message message1;
	private static Message message2;
	public TengXunXinGe(){
		message1 = new Message();
		message1.setType(Message.TYPE_NOTIFICATION);
		Style style = new Style(1);
		style = new Style(3,1,0,1,0);
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_URL);
		action.setUrl("http://xg.qq.com");
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "value1");
		custom.put("key2", 2);
		message1.setTitle("天女下凡");
		message1.setContent("哈哈哈 哥会了你妈的!");
		message1.setStyle(style);
		message1.setAction(action);
		message1.setCustom(custom);
		TimeInterval acceptTime1 = new TimeInterval(0,0,23,59);
		message1.addAcceptTime(acceptTime1);
		
		message2 = new Message();
		message2.setType(Message.TYPE_NOTIFICATION);
		message2.setTitle("title");
		message2.setContent("通知点击执行Intent测试");
		style = new Style(1);
		action.setActionType(ClickAction.TYPE_INTENT);
		action.setIntent("intent:10086#Intent;scheme=tel;action=android.intent.action.DIAL;S.key=value;end");
		message2.setStyle(style);
		message2.setAction(action);
	}
	public static void main(String[] args) {
		
		JSONObject ret;
		TengXunXinGe t = new TengXunXinGe();
		//单个设备下发透传消息
		String title="11",content="";//目前11代表通知，content不传就行,以后有别的了从12开始往后加
		ret = t.demoPushSingleDeviceMessage(title,content,token);
		System.out.println(ret);
		
		//单个设备下发通知消息
/*		ret = t.demoPushSingleDeviceNotification();
		System.out.println(ret);*/
		
		//所有设备下发通知消息
/*		ret = t.demoPushAllDeviceNotification();
		System.out.println(ret);*/
		
//		
//		ret = t.demoPushSingleDeviceIOS();
//		System.out.println(ret);
//		
		//下发单个账号没弄明白
/*		ret = t.demoPushSingleAccount();
		System.out.println(ret);*/
//		
//		ret = t.demoPushSingleAccountIOS();
//		System.out.println(ret);
//		
/*		ret = t.demoPushAllDevice();
		System.out.println(ret);*/
		
//		ret = t.demoPushTags();
//		System.out.println(ret);
//		
//		ret = t.demoQueryPushStatus();
//		System.out.println(ret);
//		
//		ret = t.demoQueryDeviceCount();
//		System.out.println(ret);
//		
//		ret = t.demoQueryTags();
//		System.out.println(ret);
//		
//		ret = t.demoQueryTagTokenNum();
//		System.out.println(ret);
//		
//		ret = t.demoQueryTokenTags();
//		System.out.println(ret);
//		
//		ret = t.demoCancelTimingPush();
//		System.out.println(ret);
//
//      ret = t.DemoBatchSetTag();
//      System.out.println(ret);
//      
//      ret = t.DemoBatchDelTag();
//      System.out.println(ret);
	}
	
	/**
	 * 单个设备下发透传消息
	 * @param title
	 * @param content
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceMessage(String title,String content,String token)
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		Message message = new Message();
		message.setTitle(title);
		message.setContent(content);
		JSONObject ret = xinge.pushSingleDevice(token, message);
		
		return ret;
	}
	
	/**
	 * 单个设备下发透传消息 content 中为json形式
	 * JSONObject json = new JSONObject();
		json.put("title", "131311");
		json.put("content", "131312313");
		message.setJson_m_content(json);
	 * @param title
	 * @param content
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceMessage(String title,JSONObject content,String token)
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		Message message = new Message();
		message.setTitle(title);
		message.setContent(content.toString());
		message.setType(Message.TYPE_MESSAGE);
		JSONObject ret = xinge.pushSingleDevice(token, message);
		
		return ret;
	}
	
	/**
	 * 单个设备下发通知消息
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceNotification()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);	
		JSONObject ret = xinge.pushSingleDevice(token, message1);
		//JSONObject ret = xinge.pushAllDevice(XingeApp.DEVICE_ALL, message1);//慢
		return (ret);
	}
	public static JSONObject demoPushSingleDeviceNotification(String title,String content,String token)
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);	
		message1 = new Message();
		message1.setType(Message.TYPE_NOTIFICATION);
		Style style = new Style(1);
		style = new Style(3,1,0,1,0);
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_URL);
		action.setUrl("http://xg.qq.com");
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "value1");
		custom.put("key2", 2);
		message1.setTitle(title);
		message1.setContent(content);
		message1.setStyle(style);
		message1.setAction(action);
		message1.setCustom(custom);
		TimeInterval acceptTime1 = new TimeInterval(0,0,23,59);
		message1.addAcceptTime(acceptTime1);
		JSONObject ret = xinge.pushSingleDevice(token, message1);
		//JSONObject ret = xinge.pushAllDevice(XingeApp.DEVICE_ALL, message1);//慢
		return (ret);
	}
	/**
	 * 所有设备下发通知消息
	 * @return
	 */
	public static JSONObject demoPushAllDeviceNotification()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);	
		JSONObject ret = xinge.pushAllDevice(XingeApp.DEVICE_ALL, message1);//慢
		return (ret);
	}
	/**
	 * 单个设备下发通知消息
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceIOS()
	{
		MessageIOS message = new MessageIOS();
		message.setAlert("ios test");
		message.setBadge(1);
		message.setSound("beep.wav");
		TimeInterval acceptTime1 = new TimeInterval(0,0,23,59);
		message.addAcceptTime(acceptTime1);
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "value1");
		custom.put("key2", 2);
		message.setCustom(custom);
		
		XingeApp xinge = new XingeApp(000L, secret_key);	
		JSONObject ret = xinge.pushSingleDevice(token, message, XingeApp.IOSENV_DEV);
		return (ret);
	}
	
	
	/**
	 *  单个设备下发通知Intent
		setIntent()的内容需要使用intent.toUri(Intent.URI_INTENT_SCHEME)方法来得到序列化后的Intent(自定义参数也包含在Intent内）
		终端收到后可通过intent.parseUri()来反序列化得到Intent
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceNotificationIntent()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);	
		JSONObject ret = xinge.pushSingleDevice(token, message2);
		return (ret);
	}
	
	/**
	 * 下发单个账号
	 * @return
	 */
	public static JSONObject demoPushSingleAccount() {
		XingeApp xinge = new XingeApp(access_id, secret_key);
		Message message = new Message();
		message.setTitle("title");
		message.setContent("下发单个账号");
		message.setType(Message.TYPE_MESSAGE);
		JSONObject ret = xinge.pushSingleAccount(XingeApp.DEVICE_ANDROID, "joelliu", message);
		return (ret);
	}
	
	/**
	 * 下发IOS单个账号
	 * @return
	 */
	public static JSONObject demoPushSingleAccountIOS() {
		MessageIOS message = new MessageIOS();
		message.setAlert("ios test");
		message.setBadge(1);
		message.setSound("beep.wav");
		TimeInterval acceptTime1 = new TimeInterval(0,0,23,59);
		message.addAcceptTime(acceptTime1);
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "value1");
		custom.put("key2", 2);
		message.setCustom(custom);
		
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.pushSingleAccount(XingeApp.DEVICE_IOS, "joelliu", message, XingeApp.IOSENV_DEV);
		return (ret);
	}
	
	/**
	 * 下发所有设备
	 * @return
	 */
	public static JSONObject demoPushAllDevice()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		Message message = new Message();
		message.setTitle("title");
		message.setContent("下发所有设备");
		message.setType(Message.TYPE_MESSAGE);
		JSONObject ret = xinge.pushAllDevice(XingeApp.DEVICE_ALL, message);
		return (ret);
	}
	
	/**
	 * 下发标签选中设备
	 * @return
	 */
	public static JSONObject demoPushTags()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		List<String> tagList = new ArrayList<String>();
		tagList.add("joelliu");
		tagList.add("phone");
		JSONObject ret = xinge.pushTags(XingeApp.DEVICE_ANDROID, tagList, "OR", message1);
		return (ret);
	}
	
	/**
	 * 查询消息推送状态
	 * @return
	 */
	public static JSONObject demoQueryPushStatus()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		List<String> pushIdList = new ArrayList<String>();
		pushIdList.add("390");
		pushIdList.add("389");
		JSONObject ret = xinge.queryPushStatus(pushIdList);
		return (ret);
	}
	/**
	 * 查询设备数量
	 * @return
	 */
	public static JSONObject demoQueryDeviceCount()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.queryDeviceCount();
		return (ret);
	}
	/**
	 * 查询标签
	 * @return
	 */
	public static JSONObject demoQueryTags()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.queryTags();
		return (ret);
	}
	/**
	 * 查询某个tag下token的数量
	 * @return
	 */
	public static JSONObject demoQueryTagTokenNum()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.queryTagTokenNum("tag");
		return (ret);
	}
	/**
	 * 查询某个token的标签
	 * @return
	 */
	public static JSONObject demoQueryTokenTags()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.queryTokenTags(token);
		return (ret);
	}
	/**
	 * 取消定时任务
	 * @return
	 */
	public static JSONObject demoCancelTimingPush()
	{
		XingeApp xinge = new XingeApp(access_id, secret_key);
		JSONObject ret = xinge.cancelTimingPush("32");
		return (ret);
	}
	/**
	 *  设置标签
	 * @return
	 */
	public static JSONObject DemoBatchSetTag()
    {
        XingeApp xinge = new XingeApp(access_id, secret_key);

        List<TagTokenPair> pairs = new ArrayList<TagTokenPair>();

        // 切记把这里的示例tag和示例token修改为你的真实tag和真实token
	    pairs.add(new TagTokenPair("tag1",token));
        pairs.add(new TagTokenPair("tag2",token));

        JSONObject ret = xinge.BatchSetTag(pairs);
        return (ret);
    }

	/**
	 *  删除标签
	 * @return
	 */
    public static JSONObject DemoBatchDelTag()
    {
        XingeApp xinge = new XingeApp(access_id, secret_key);

        List<TagTokenPair> pairs = new ArrayList<TagTokenPair>();

        // 切记把这里的示例tag和示例token修改为你的真实tag和真实token
        pairs.add(new TagTokenPair("tag1","token00000000000000000000000000000000001"));
        pairs.add(new TagTokenPair("tag2","token00000000000000000000000000000000001"));
        
        JSONObject ret = xinge.BatchDelTag(pairs);
        
        return (ret);
    }

}
