package com.daren.cooperate.core.model;

public class ChatIndex {
	private long from_userid;
	private long to_userid;
	private long chat_id;
	private long group_id;
	private String name="";
	private String neartime;
	private String chat_content="";
	private String logo="";
	private String unsearch_userid="";//判断删除最近一次聊天记录用
	private int chat_count;
	private int hasnews;//0无新消息 1 有新消息
	private int messagetype =3;//1.通知 2.讨论组3.点对点消息
	private int chat_type;
	private int group_creater;//0不是圈子创建人员 1是圈子创建人员
	
	public ChatIndex(){}
	
	public ChatIndex(long from_userid, long to_userid, String neartime) {
		super();
		this.from_userid = from_userid;
		this.to_userid = to_userid;
		this.neartime = neartime;
	}
	public ChatIndex(long from_userid, long to_userid,long group_id, String neartime) {
		super();
		this.from_userid = from_userid;
		this.to_userid = to_userid;
		this.group_id = group_id;
		this.neartime = neartime;
	}

	public ChatIndex(long from_userid, long to_userid, String neartime,String chat_content,
			int chat_count) {
		super();
		this.from_userid = from_userid;
		this.to_userid = to_userid;
		this.neartime = neartime;
		this.chat_content = chat_content;
		this.chat_count = chat_count;
	}

	public long getFrom_userid() {
		return from_userid;
	}

	public void setFrom_userid(long from_userid) {
		this.from_userid = from_userid;
	}

	public long getTo_userid() {
		return to_userid;
	}

	public void setTo_userid(long to_userid) {
		this.to_userid = to_userid;
	}

	public String getNeartime() {
		return neartime;
	}

	public void setNeartime(String neartime) {
		this.neartime = neartime;
	}

	public int getChat_count() {
		return chat_count;
	}

	public void setChat_count(int chat_count) {
		this.chat_count = chat_count;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public int getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(int messagetype) {
		this.messagetype = messagetype;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public int getHasnews() {
		return hasnews;
	}

	public void setHasnews(int hasnews) {
		this.hasnews = hasnews;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getChat_type() {
		return chat_type;
	}

	public void setChat_type(int chat_type) {
		this.chat_type = chat_type;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup_creater() {
		return group_creater;
	}

	public void setGroup_creater(int group_creater) {
		this.group_creater = group_creater;
	}

	public String getUnsearch_userid() {
		return unsearch_userid;
	}

	public void setUnsearch_userid(String unsearch_userid) {
		this.unsearch_userid = unsearch_userid;
	}

	@Override
	public String toString() {
		return "ChatIndex [from_userid=" + from_userid + ", to_userid="
				+ to_userid + ", chat_id=" + chat_id + ", group_id=" + group_id
				+ ", name=" + name + ", neartime=" + neartime
				+ ", chat_content=" + chat_content + ", logo=" + logo
				+ ", unsearch_userid=" + unsearch_userid + ", chat_count="
				+ chat_count + ", hasnews=" + hasnews + ", messagetype="
				+ messagetype + ", chat_type=" + chat_type + ", group_creater="
				+ group_creater + "]";
	}
	
}
