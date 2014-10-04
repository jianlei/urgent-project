package com.daren.enterprise.webapp.wicket;

/**
 * @类描述：常量定义类
 * @创建人： sunlingfeng
 * @创建时间：2014/10/4
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class Const {
    //权限常量
    public final static String PERMISSION_INFO = "info"; //顶级菜单

    public final static String PERMISSION_INFO_ENT= "info:ent"; //企业信息子菜单
    public final static String PERMISSION_INFO_ENT_ADD = "info:ent:add"; //企业信息子菜单-新增
    public final static String PERMISSION_INFO_ENT_EDIT = "info:ent:edit"; //企业信息子菜单-修改
    public final static String PERMISSION_INFO_ENT_DELETE = "info:ent:delete"; //企业信息子菜单-删除

    public final static String PERMISSION_INFO_ORG= "info:org"; //监管机构子菜单
    public final static String PERMISSION_INFO_ORG_ADD = "info:org:add"; //监管机构子菜单-新增
    public final static String PERMISSION_INFO_ORG_EDIT = "info:org:edit"; //监管机构子菜单-修改
    public final static String PERMISSION_INFO_ORG_DELETE = "info:org:delete"; //监管机构子菜单-删除

    //菜单常量
    public final static String MENU_INFO = "信息管理"; //顶级菜单
    public final static String MENU_INFO_ENT = "企业信息管理";
    public final static String MENU_INFO_ORG = "监管机构管理";

    //菜单显示顺序常量
    public static final int INDEX_INFO_ENT  = 0;
    public static final int INDEX_INFO_ORG  = 10;
}
