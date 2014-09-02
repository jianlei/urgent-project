package com.daren.core.api;

/**
 * 系统常量定义
 * Created by sunlingfeng on 2014/8/21.
 */
public interface IConst {
    public final static String URGENT_WICKET_APPLICATION_NAME = "daren.project.urgent"; //应急系统
    public final static String SYSTEM_WICKET_APPLICATION_NAME = "daren.project.system";//系统配置
    public final static String EXAMPLE_WICKET_APPLICATION_NAME = "daren.project.example";//例子
    public final static String GOVERNMENT_WICKET_APPLICATION_NAME = "daren.project.government";//政务
    /*public final static String OFFICE_WEB_PATH_READ = "http://localhost:8080/tempfile/";
    public final static String OFFICE_WEB_PATH_WRITE = "D:/apache-tomcat/webapps/uploadfile/";
    public final static String OFFICE_WEB_PATH_TEMP = "D:/apache-tomcat/webapps/tempfile/";*/
    //release config
    public final static String OFFICE_WEB_PATH_READ = "http://202.98.7.181:8687/tempfile/";
    public final static String OFFICE_WEB_PATH_WRITE = "E:\\web\\tomcat_7.0.27_8687\\webapps\\uploadfile\\";
    public final static String OFFICE_WEB_PATH_TEMP = "E:\\web\\tomcat_7.0.27_8687\\webapps\\tempfile\\";

}
