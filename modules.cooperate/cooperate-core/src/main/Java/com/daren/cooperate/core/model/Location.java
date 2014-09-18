package com.daren.cooperate.core.model;

public class Location {

	private long location_id;
	private double lng;
	private double lat;
	private double c_lng;
	private double c_lat;
	private double speed;
	private double direction;
	private double height;
	private String client_time;
	private String location_desc;
	private long user_id;
	private int parent_type;
	private long parent_id;
	private int status;
	
	public Location(){}
	
	public Location(long location_id, double lng, double lat, double c_lng,
			double c_lat, double speed, double direction, double height,
			String client_time, String location_desc, long user_id,
			int parent_type, long parent_id, int status) {
		super();
		this.location_id = location_id;
		this.lng = lng;
		this.lat = lat;
		this.c_lng = c_lng;
		this.c_lat = c_lat;
		this.speed = speed;
		this.direction = direction;
		this.height = height;
		this.client_time = client_time;
		this.location_desc = location_desc;
		this.user_id = user_id;
		this.parent_type = parent_type;
		this.parent_id = parent_id;
		this.status = status;
	}

	public long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(long location_id) {
		this.location_id = location_id;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getC_lng() {
		return c_lng;
	}

	public void setC_lng(double c_lng) {
		this.c_lng = c_lng;
	}

	public double getC_lat() {
		return c_lat;
	}

	public void setC_lat(double c_lat) {
		this.c_lat = c_lat;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getClient_time() {
		return client_time;
	}

	public void setClient_time(String client_time) {
		this.client_time = client_time;
	}

	public String getLocation_desc() {
		return location_desc;
	}

	public void setLocation_desc(String location_desc) {
		this.location_desc = location_desc;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getParent_type() {
		return parent_type;
	}

	public void setParent_type(int parent_type) {
		this.parent_type = parent_type;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Location [location_id=" + location_id + ", lng=" + lng
				+ ", lat=" + lat + ", c_lng=" + c_lng + ", c_lat=" + c_lat
				+ ", speed=" + speed + ", direction=" + direction + ", height="
				+ height + ", client_time=" + client_time + ", location_desc="
				+ location_desc + ", user_id=" + user_id + ", parent_type="
				+ parent_type + ", parent_id=" + parent_id + ", status="
				+ status + "]";
	}
		
}
