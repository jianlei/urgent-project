package com.daren.admin.webapp.wicket;

/**
 * @类描述：${INPUT}
 * @创建人： sunlingfeng
 * @创建时间：2014/10/4
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class Const {
    //权限常量
    public final static String PERMISSION_ADMIN = "sys:root"; //顶级菜单

    public final static String PERMISSION_ADMIN_USER = "sys:user"; //用户子菜单
    public final static String PERMISSION_ADMIN_USER_ADD = "sys:user:add"; //用户子菜单-新增
    public final static String PERMISSION_ADMIN_USER_EDIT = "sys:user:edit"; //用户子菜单-修改
    public final static String PERMISSION_ADMIN_USER_DELETE = "sys:user:delete"; //用户子菜单-删除

    public final static String PERMISSION_ADMIN_ROLE = "sys:role"; //角色子菜单
    public final static String PERMISSION_ADMIN_ROLE_ADD = "sys:role:add"; //角色子菜单-新增
    public final static String PERMISSION_ADMIN_ROLE_EDIT = "sys:role:edit"; //角色子菜单-修改
    public final static String PERMISSION_ADMIN_ROLE_DELETE = "sys:role:delete"; //角色子菜单-删除

    public final static String PERMISSION_ADMIN_AREA = "sys:area"; //区域子菜单
    public final static String PERMISSION_ADMIN_AREA_ADD = "sys:area:add"; //区域子菜单-新增
    public final static String PERMISSION_ADMIN_AREA_EDIT = "sys:area:edit"; //区域子菜单-修改
    public final static String PERMISSION_ADMIN_AREA_DELETE = "sys:area:delete"; //区域子菜单-删除

    public final static String PERMISSION_ADMIN_PERMISSION = "sys:permission"; //权限子菜单
    public final static String PERMISSION_ADMIN_PERMISSION_ADD = "sys:permission:add"; //权限子菜单-新增
    public final static String PERMISSION_ADMIN_PERMISSION_EDIT = "sys:permission:edit"; //权限子菜单-修改
    public final static String PERMISSION_ADMIN_PERMISSION_DELETE = "sys:permission:delete"; //权限子菜单-删除

    public final static String PERMISSION_ADMIN_DICT = "sys:dict"; //字典子菜单
    public final static String PERMISSION_ADMIN_DICT_ADD = "sys:dict:add"; //字典子菜单-新增
    public final static String PERMISSION_ADMIN_DICT_EDIT = "syns:dict:edit"; //字典子菜单-修改
    public final static String PERMISSION_ADMIN_DICT_DELETE = "sys:dict:delete"; //字典子菜单-删除

    public final static String PERMISSION_ADMIN_ENT_USER = "info:entuser"; //企业用户子菜单
    public final static String PERMISSION_ADMIN_ENT_USER_ADD = "info:entuser:add"; //企业用户子菜单-新增
    public final static String PERMISSION_ADMIN_ENT_USER_EDIT = "info:entuser:edit"; //企业用户子菜单-修改
    public final static String PERMISSION_ADMIN_ENT_USER_DELETE = "info:entuser:delete"; //企业用户子菜单-删除

    //菜单常量
    public final static String MENU_ADMIN = "系统管理"; //顶级菜单
    public final static String MENU_ADMIN_USER = "用户管理"; //用户子菜单
    public final static String MENU_ADMIN_AREA ="区域管理";
    public final static String MENU_ADMIN_PERMISSION ="权限管理";
    public final static String MENU_ADMIN_DICT ="字典管理";
    public final static String MENU_ADMIN_ENT_USER ="企业用户管理";
    public final static String MENU_ADMIN_ROLE ="角色管理";

    //菜单显示顺序常量
    public static final int INDEX_ADMIN_USER = 0;
    public static final int INDEX_ADMIN_ROLE = 10;
    public static final int INDEX_ADMIN_PERMISSION  = 15;
    public static final int INDEX_ADMIN_AREA = 20;
    public static final int INDEX_ADMIN_DICT  = 30;

    public static final int INDEX_ADMIN_ENT_USER = 0;
}
