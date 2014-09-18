package com.daren.cooperate.core.util;

import org.json.JSONObject;

import java.util.List;




/**
 * 通过信鸽发送消息线程
 * @author Administrator
 *
 */
public class SendMsgByXingeThread implements Runnable {
	
	private List<String> pushAllUserList = null;
	private Integer type = null;    		 //1:单个设备下发透传消息
	private String pushtitle="11";           //目前11代表通知
	private String pushcontent;           //content不传就行,以后有别的了从12开始往后加
	private JSONObject pushjsoncontent;           //content不传就行,以后有别的了从12开始往后加
	private String userToken="";
	
	public SendMsgByXingeThread(){
		
	}
	
	public SendMsgByXingeThread(List<String> pushAllUserList,Integer type,String pushtitle,String pushcontent,JSONObject pushjsoncontent){
		this.pushAllUserList = pushAllUserList;
		this.type = type;
		this.pushtitle = pushtitle;
		this.pushcontent = pushcontent;
		this.pushjsoncontent = pushjsoncontent;
	}
	
	public SendMsgByXingeThread(String userToken,Integer type,String pushtitle,String pushcontent,JSONObject pushjsoncontent){
		this.userToken = userToken;
		this.type = type;
		this.pushtitle = pushtitle;
		this.pushcontent = pushcontent;
		this.pushjsoncontent = pushjsoncontent;
	}

	public void run() {
		boolean sendFlag = false;
		int sendNum = 0;
		while(!sendFlag&&sendNum<5){
			sendFlag = sendMsg();
			sendNum++;
		}
	}
	/**
	 * 发送消息
	 * @return
	 */
	private boolean sendMsg(){
		boolean flag = false;
		//push 推送
		if((this.pushAllUserList!=null && (type==1||type==2))||((!"".equals(userToken))&&type==3) ){
			switch(type){
				case 1:{
					for (int i = 0; i < this.pushAllUserList.size(); i++) {
						String token = this.pushAllUserList.get(i);
						//JSONObject demoPushSingleDeviceMessage = TengXunXinGe.demoPushSingleDeviceMessage(this.pushtitle, this.pushcontent,token);
						JSONObject demoPushSingleDeviceMessage = TengXunXinGe.demoPushSingleDeviceMessage(this.pushtitle, this.pushjsoncontent,token);
						System.out.println("--------------信鸽推送-------->"+demoPushSingleDeviceMessage);
					}
					break;
				}
				case 2:{
					for (int i = 0; i < this.pushAllUserList.size(); i++) {
						String token = this.pushAllUserList.get(i);
						JSONObject demoPushSingleDeviceMessage = TengXunXinGe.demoPushSingleDeviceNotification(this.pushtitle, this.pushcontent,token);
						System.out.println("--------------信鸽推送-------->"+demoPushSingleDeviceMessage);
					}
					break;
				}
				//单个人的穿透消息发送
				case 3:{
					JSONObject demoPushSingleDeviceMessage = TengXunXinGe.demoPushSingleDeviceMessage(this.pushtitle, this.pushjsoncontent,userToken);
					System.out.println("--------------信鸽推送-------->"+demoPushSingleDeviceMessage);
					break;
				}
				default : {
					System.out.println("输入的参数类型有误！");
				}
			}
			flag = true;
		}
		return flag;
	}

}
