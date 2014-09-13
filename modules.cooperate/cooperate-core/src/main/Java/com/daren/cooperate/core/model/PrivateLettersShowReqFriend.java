package com.daren.cooperate.core.model;

public class PrivateLettersShowReqFriend {
	private String nickname;
	private long req_userid;
	private long req_id;
	private String logo;
	
	public PrivateLettersShowReqFriend(){}

	public PrivateLettersShowReqFriend(String nickname, long req_userid,
			long req_id, String logo) {
		super();
		this.nickname = nickname;
		this.req_userid = req_userid;
		this.req_id = req_id;
		this.logo = logo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getReq_userid() {
		return req_userid;
	}

	public void setReq_userid(long req_userid) {
		this.req_userid = req_userid;
	}

	public long getReq_id() {
		return req_id;
	}

	public void setReq_id(long req_id) {
		this.req_id = req_id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "PrivateLettersShowReqFriend [nickname=" + nickname
				+ ", req_userid=" + req_userid + ", req_id=" + req_id
				+ ", logo=" + logo + "]";
	}

	
}
