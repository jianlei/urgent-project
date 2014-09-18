package com.daren.cooperate.core.model;

public class ChatListForIndex {
	private String neartime;
	private String chat_content;
	private long from_user_id;
	private long to_user_id;
	private String attache_spicurl;
	private String chat_voice;
	private long chat_id;
	private int chat_type;
	private int hasnews;
	private long group_manager_user;
	
	public ChatListForIndex(){}

	public ChatListForIndex(String neartime, String chat_content,
			long from_user_id, long to_user_id, String attache_spicurl,
			String chat_voice, long chat_id) {
		super();
		this.neartime = neartime;
		this.chat_content = chat_content;
		this.from_user_id = from_user_id;
		this.to_user_id = to_user_id;
		this.attache_spicurl = attache_spicurl;
		this.chat_voice = chat_voice;
		this.chat_id = chat_id;
	}

	public String getNeartime() {
		return neartime;
	}

	public void setNeartime(String neartime) {
		this.neartime = neartime;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public long getFrom_user_id() {
		return from_user_id;
	}

	public void setFrom_user_id(long from_user_id) {
		this.from_user_id = from_user_id;
	}

	public long getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(long to_user_id) {
		this.to_user_id = to_user_id;
	}

	public String getAttache_spicurl() {
		return attache_spicurl;
	}

	public void setAttache_spicurl(String attache_spicurl) {
		this.attache_spicurl = attache_spicurl;
	}

	public String getChat_voice() {
		return chat_voice;
	}

	public void setChat_voice(String chat_voice) {
		this.chat_voice = chat_voice;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public int getChat_type() {
		return chat_type;
	}

	public void setChat_type(int chat_type) {
		this.chat_type = chat_type;
	}

	public int getHasnews() {
		return hasnews;
	}

	public void setHasnews(int hasnews) {
		this.hasnews = hasnews;
	}

	public long getGroup_manager_user() {
		return group_manager_user;
	}

	public void setGroup_manager_user(long group_manager_user) {
		this.group_manager_user = group_manager_user;
	}

	@Override
	public String toString() {
		return "ChatListForIndex [neartime=" + neartime + ", chat_content="
				+ chat_content + ", from_user_id=" + from_user_id
				+ ", to_user_id=" + to_user_id + ", attache_spicurl="
				+ attache_spicurl + ", chat_voice=" + chat_voice + ", chat_id="
				+ chat_id + ", chat_type=" + chat_type + ", hasnews=" + hasnews
				+ ", group_manager_user=" + group_manager_user + "]";
	}

}
