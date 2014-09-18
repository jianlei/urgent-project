package com.daren.cooperate.core.model;

public class Chat {

	private long chat_id;
	private long group_id;
	private int chat_type;
	private int is_read;
	private String chat_content;
	private String chat_attach;
	private String picture_url;
	private long to_userid;
	private long from_userid;
	private String chat_lat;
	private String chat_lng;
	private String chat_time;
	private String voice_time;
	
	public Chat(){}

	public Chat(int chat_type, int is_read, String chat_content,
			String chat_attach, String picture_url, long to_userid,
			long from_userid, String chat_lat, String chat_lng,String voice_time) {
		super();
		this.chat_type = chat_type;
		this.is_read = is_read;
		this.chat_content = chat_content;
		this.chat_attach = chat_attach;
		this.picture_url = picture_url;
		this.to_userid = to_userid;
		this.from_userid = from_userid;
		this.chat_lat = chat_lat;
		this.chat_lng = chat_lng;
		this.voice_time = voice_time;
	}
	public Chat(long group_id,int chat_type, int is_read, String chat_content,
			String chat_attach, String picture_url,long from_userid, String chat_lat, String chat_lng,String voice_time) {
		super();
		this.group_id = group_id;
		this.chat_type = chat_type;
		this.is_read = is_read;
		this.chat_content = chat_content;
		this.chat_attach = chat_attach;
		this.picture_url = picture_url;
		this.from_userid = from_userid;
		this.chat_lat = chat_lat;
		this.chat_lng = chat_lng;
		this.voice_time = voice_time;
	}
	public Chat(long chat_id, int chat_type, int is_read, String chat_content,
			String chat_attach, String picture_url, long to_userid,
			long from_userid, String chat_lat, String chat_lng) {
		super();
		this.chat_id = chat_id;
		this.chat_type = chat_type;
		this.is_read = is_read;
		this.chat_content = chat_content;
		this.chat_attach = chat_attach;
		this.picture_url = picture_url;
		this.to_userid = to_userid;
		this.from_userid = from_userid;
		this.chat_lat = chat_lat;
		this.chat_lng = chat_lng;
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

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public String getChat_attach() {
		return chat_attach;
	}

	public void setChat_attach(String chat_attach) {
		this.chat_attach = chat_attach;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public long getTo_userid() {
		return to_userid;
	}

	public void setTo_userid(long to_userid) {
		this.to_userid = to_userid;
	}

	public long getFrom_userid() {
		return from_userid;
	}

	public void setFrom_userid(long from_userid) {
		this.from_userid = from_userid;
	}

	public String getChat_lat() {
		return chat_lat;
	}

	public void setChat_lat(String chat_lat) {
		this.chat_lat = chat_lat;
	}

	public String getChat_lng() {
		return chat_lng;
	}

	public void setChat_lng(String chat_lng) {
		this.chat_lng = chat_lng;
	}

	public String getChat_time() {
		return chat_time;
	}

	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public String getVoice_time() {
		return voice_time;
	}

	public void setVoice_time(String voice_time) {
		this.voice_time = voice_time;
	}

	@Override
	public String toString() {
		return "Chat [chat_id=" + chat_id + ", group_id=" + group_id
				+ ", chat_type=" + chat_type + ", is_read=" + is_read
				+ ", chat_content=" + chat_content + ", chat_attach="
				+ chat_attach + ", picture_url=" + picture_url + ", to_userid="
				+ to_userid + ", from_userid=" + from_userid + ", chat_lat="
				+ chat_lat + ", chat_lng=" + chat_lng + ", chat_time="
				+ chat_time + ", voice_time=" + voice_time + "]";
	}
	
}
