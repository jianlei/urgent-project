package com.daren.cooperate.core.model;

import java.io.Serializable;
import java.util.Date;

public class User  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long user_id;
	private String user_name="";
	private String password="";
	private String salt = "";
	private String signet = "";
	private String nickname = "";
	private String name = "";
	private int comp_id;
	private int part;
	private int score;
	private String comp_name = "";
	private String mobile = "";
	private String email = "";
	private int role_id;
	private int client_id;
	private Date enrolltime;
	private String enrolltimestr = "";
	private String head_photo = "";
	private String head_spicurl = "";
	private String user_tblextend = "";
	private int status;
	private int role_weight;
	private String cityname = "";
	private String next_paytime = "";
	private int hour_start;
	private int hour_end;
	private int group_type;
	private String g_name = "";
	private String push_userid = "";
	private String token = "";
	private int os;
	private int isthreshold;
	private int distance;
	private int istrack;
	private int track_start;
	private int isplan;
	private int track_end;
	private int isfocus;
	private int in_time;
	private String in_location_lat = "";
	private String in_location_lng = "";
	private int off_time;
	private String off_location_lat = "";
	private String off_location_lng = "";
	private int strategy;//主动打卡还是点名
	private String compname = "";
	private int isfactory;//是否是厂家
	private int just_government;//只看官方标识 0看所有 1只看官方 
	private double card_id;//身份证号码

	public User() {}

	public User(long user_id, String user_name, String password, String salt, String signet, String nickname, int comp_id, String mobile, String email, int role_id, int client_id, Date enrolltime, String enrolltimestr, String head_photo, String head_spicurl, String user_tblextend, int status, int role_weight, String cityname, String next_paytime, int hour_start, int hour_end, int group_type, String g_name, String push_userid, String token, int os, int isthreshold, int distance, int istrack, int track_start, int isplan, int track_end, int isfocus) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.salt = salt;
		this.signet = signet;
		this.nickname = nickname;
		this.comp_id = comp_id;
		this.mobile = mobile;
		this.email = email;
		this.role_id = role_id;
		this.client_id = client_id;
		this.enrolltime = enrolltime;
		this.enrolltimestr = enrolltimestr;
		this.head_photo = head_photo;
		this.head_spicurl = head_spicurl;
		this.user_tblextend = user_tblextend;
		this.status = status;
		this.role_weight = role_weight;
		this.cityname = cityname;
		this.next_paytime = next_paytime;
		this.hour_start = hour_start;
		this.hour_end = hour_end;
		this.group_type = group_type;
		this.g_name = g_name;
		this.push_userid = push_userid;
		this.token = token;
		this.os = os;
		this.isthreshold = isthreshold;
		this.distance = distance;
		this.istrack = istrack;
		this.track_start = track_start;
		this.isplan = isplan;
		this.track_end = track_end;
		this.isfocus = isfocus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignet() {
		return signet;
	}

	public void setSignet(String signet) {
		this.signet = signet;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getComp_id() {
		return comp_id;
	}

	public void setComp_id(int comp_id) {
		this.comp_id = comp_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public Date getEnrolltime() {
		return enrolltime;
	}

	public void setEnrolltime(Date enrolltime) {
		this.enrolltime = enrolltime;
	}

	public String getEnrolltimestr() {
		return enrolltimestr;
	}

	public void setEnrolltimestr(String enrolltimestr) {
		this.enrolltimestr = enrolltimestr;
	}

	public String getHead_photo() {
		return head_photo;
	}

	public void setHead_photo(String head_photo) {
		this.head_photo = head_photo;
	}

	public String getHead_spicurl() {
		return head_spicurl;
	}

	public void setHead_spicurl(String head_spicurl) {
		this.head_spicurl = head_spicurl;
	}

	public String getUser_tblextend() {
		return user_tblextend;
	}

	public void setUser_tblextend(String user_tblextend) {
		this.user_tblextend = user_tblextend;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getRole_weight() {
		return role_weight;
	}

	public void setRole_weight(int role_weight) {
		this.role_weight = role_weight;
	}

	public String getNext_paytime() {
		return next_paytime;
	}

	public void setNext_paytime(String next_paytime) {
		this.next_paytime = next_paytime;
	}

	public int getHour_start() {
		return hour_start;
	}

	public void setHour_start(int hour_start) {
		this.hour_start = hour_start;
	}

	public int getHour_end() {
		return hour_end;
	}

	public void setHour_end(int hour_end) {
		this.hour_end = hour_end;
	}

	public int getGroup_type() {
		return group_type;
	}

	public void setGroup_type(int group_type) {
		this.group_type = group_type;
	}

	public String getPush_userid() {
		return push_userid;
	}

	public void setPush_userid(String push_userid) {
		this.push_userid = push_userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public int getIsthreshold() {
		return isthreshold;
	}

	public void setIsthreshold(int isthreshold) {
		this.isthreshold = isthreshold;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getIstrack() {
		return istrack;
	}

	public void setIstrack(int istrack) {
		this.istrack = istrack;
	}

	public int getTrack_start() {
		return track_start;
	}

	public void setTrack_start(int track_start) {
		this.track_start = track_start;
	}

	public int getIsplan() {
		return isplan;
	}

	public void setIsplan(int isplan) {
		this.isplan = isplan;
	}

	public int getTrack_end() {
		return track_end;
	}

	public void setTrack_end(int track_end) {
		this.track_end = track_end;
	}

	public int getIsfocus() {
		return isfocus;
	}

	public void setIsfocus(int isfocus) {
		this.isfocus = isfocus;
	}

	public String getG_name() {
		return g_name;
	}

	public void setG_name(String g_name) {
		this.g_name = g_name;
	}

	public int getIn_time() {
		return in_time;
	}

	public void setIn_time(int in_time) {
		this.in_time = in_time;
	}

	public String getIn_location_lat() {
		return in_location_lat;
	}

	public void setIn_location_lat(String in_location_lat) {
		this.in_location_lat = in_location_lat;
	}

	public String getIn_location_lng() {
		return in_location_lng;
	}

	public void setIn_location_lng(String in_location_lng) {
		this.in_location_lng = in_location_lng;
	}

	public int getOff_time() {
		return off_time;
	}

	public void setOff_time(int off_time) {
		this.off_time = off_time;
	}

	public String getOff_location_lat() {
		return off_location_lat;
	}

	public void setOff_location_lat(String off_location_lat) {
		this.off_location_lat = off_location_lat;
	}

	public String getOff_location_lng() {
		return off_location_lng;
	}

	public void setOff_location_lng(String off_location_lng) {
		this.off_location_lng = off_location_lng;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}

	public int getIsfactory() {
		return isfactory;
	}

	public void setIsfactory(int isfactory) {
		this.isfactory = isfactory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComp_name() {
		return comp_name;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public int getJust_government() {
		return just_government;
	}

	public void setJust_government(int just_government) {
		this.just_government = just_government;
	}

	public double getCard_id() {
		return card_id;
	}

	public void setCard_id(double card_id) {
		this.card_id = card_id;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name
				+ ", password=" + password + ", salt=" + salt + ", signet="
				+ signet + ", nickname=" + nickname + ", name=" + name
				+ ", comp_id=" + comp_id + ", part=" + part + ", score="
				+ score + ", comp_name=" + comp_name + ", mobile=" + mobile
				+ ", email=" + email + ", role_id=" + role_id + ", client_id="
				+ client_id + ", enrolltime=" + enrolltime + ", enrolltimestr="
				+ enrolltimestr + ", head_photo=" + head_photo
				+ ", head_spicurl=" + head_spicurl + ", user_tblextend="
				+ user_tblextend + ", status=" + status + ", role_weight="
				+ role_weight + ", cityname=" + cityname + ", next_paytime="
				+ next_paytime + ", hour_start=" + hour_start + ", hour_end="
				+ hour_end + ", group_type=" + group_type + ", g_name="
				+ g_name + ", push_userid=" + push_userid + ", token=" + token
				+ ", os=" + os + ", isthreshold=" + isthreshold + ", distance="
				+ distance + ", istrack=" + istrack + ", track_start="
				+ track_start + ", isplan=" + isplan + ", track_end="
				+ track_end + ", isfocus=" + isfocus + ", in_time=" + in_time
				+ ", in_location_lat=" + in_location_lat + ", in_location_lng="
				+ in_location_lng + ", off_time=" + off_time
				+ ", off_location_lat=" + off_location_lat
				+ ", off_location_lng=" + off_location_lng + ", strategy="
				+ strategy + ", compname=" + compname + ", isfactory="
				+ isfactory + ", just_government=" + just_government
				+ ", card_id=" + card_id + "]";
	}

}