package com.daren.cooperate.core.model;

public class SingleChatContent {
	private long chat_id;
	private String chat_content="";
	private String chat_time;
	private String voice_time="";
	private String attache_url="";
	private String attache_spicurl;
	private String logo="";
	private String name="";
	private double chat_lat;
	private double chat_lng;
	private boolean isComMess;
	private long user_id;
	private int chat_type;
	private int is_read;

	public SingleChatContent(){}

	public SingleChatContent(String chat_content,
			String chat_time, String attache_url,
			String logo, boolean isComMess, long user_id) {
		super();
		this.chat_content = chat_content;
		this.chat_time = chat_time;
		this.attache_url = attache_url;
		this.logo = logo;
		this.isComMess = isComMess;
		this.user_id = user_id;
	}

    public String getAttache_spicurl() {
        return attache_spicurl;
    }

    public void setAttache_spicurl(String attache_spicurl) {
        this.attache_spicurl = attache_spicurl;
    }

    public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public String getChat_time() {
		return chat_time;
	}

	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}

	public String getAttache_url() {
		return attache_url;
	}

	public void setAttache_url(String attache_url) {
		this.attache_url = attache_url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public boolean isComMess() {
		return isComMess;
	}

	public void setComMess(boolean isComMess) {
		this.isComMess = isComMess;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getChat_type() {
		return chat_type;
	}

	public void setChat_type(int chat_type) {
		this.chat_type = chat_type;
	}

	public double getChat_lat() {
		return chat_lat;
	}

	public void setChat_lat(double chat_lat) {
		this.chat_lat = chat_lat;
	}

	public double getChat_lng() {
		return chat_lng;
	}

	public void setChat_lng(double chat_lng) {
		this.chat_lng = chat_lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getChat_id() {
		return chat_id;
	}

	public void setChat_id(long chat_id) {
		this.chat_id = chat_id;
	}

	public String getVoice_time() {
		return voice_time;
	}

	public void setVoice_time(String voice_time) {
		this.voice_time = voice_time;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

	@Override
	public String toString() {
		return "SingleChatContent [chat_id=" + chat_id + ", chat_content="
				+ chat_content + ", chat_time=" + chat_time + ", voice_time="
				+ voice_time + ", attache_url=" + attache_url + ", logo="
				+ logo + ", name=" + name + ", chat_lat=" + chat_lat
				+ ", chat_lng=" + chat_lng + ", isComMess=" + isComMess
				+ ", user_id=" + user_id + ", chat_type=" + chat_type
				+ ", is_read=" + is_read + "]";
	}
	
}
