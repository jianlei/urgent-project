package com.daren.cooperate.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 计算服务器当前时间与用户登录的时间差
 * @author DLW
 */
public class TimeDifference {
	
	/**
	 * @param time  登录的时间
	 * @return 时间差
	 */
	public static String getTimeDifdference(String time) {
		String num = "1";
		String oneday = "24";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1;
		try {
			d1 = new Date();
			Date d2 = df.parse(time);
			long diff = d1.getTime() - d2.getTime();
			long days = diff / (1000);// 一共多少秒
			// 判断是否是秒
			if (days <= 60) {
				if (days == 60) {
					return num + "分钟之前";
				}
				return String.valueOf(days)+ "秒之前";
			}
			// 判断是否是分钟
			if (days / 60 <= 60) {
				if (days / 60 == 60) {
					return num + "小时之前";
				}
				return String.valueOf(days / 60) + "分钟之前";
			}
			// 判断是否是小时
			if (days / 3600 <= 24) {
				if (days / 3600 == 24) {
					return oneday + "小时之前";
				} else {
					float hours = days / 3600f;
					if (hours == 0.0f) {
						String lasthours = String.valueOf(hours).substring(0,String.valueOf(hours).indexOf(".") - 1);
						return lasthours + "小时之前";
						} else {
						String lasthours = String.valueOf(hours).substring(0,String.valueOf(hours).indexOf("."));
						return lasthours + "小时之前";
					}
				}
			} else {
				//return oneday + "小时之前";
				return time.substring(0, 16);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 聊天记录时间判断
	 * @param time  登录的时间 
	 * @return 时间差
	 */
	public static String getChatTimeDifdference(String time) {
		String num = "1";
		String oneday = "24";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1;
		try {
			d1 = new Date();
			Date d2 = df.parse(time);
			long diff = d1.getTime() - d2.getTime();
			long days = diff / (1000);// 一共多少秒
			// 判断是否是秒
			if (days <= 60) {
				/*if (days == 60) {
					return num + "分钟之前";
				}
				return String.valueOf(days)+ "秒之前";*/
				return "";
			}
			// 判断是否是分钟
			if (days / 60 <= 60) {
				if (days / 60 == 60) {
					return num + "小时之前";
				}
				return String.valueOf(days / 60) + "分钟之前";
			}
			// 判断是否是小时
			if (days / 3600 <= 24) {
				if (days / 3600 == 24) {
					return oneday + "小时之前";
				} else {
					float hours = days / 3600f;
					if (hours == 0.0f) {
						String lasthours = String.valueOf(hours).substring(0,String.valueOf(hours).indexOf(".") - 1);
						return lasthours + "小时之前";
					} else {
						String lasthours = String.valueOf(hours).substring(0,String.valueOf(hours).indexOf("."));
						return lasthours + "小时之前";
					}
				}
			} else {
				//return oneday + "小时之前";
				return time.substring(0, 16);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
}
