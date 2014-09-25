package com.daren.company.webapp.wicket.model;

import org.apache.wicket.util.io.IClusterable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by Administrator on 2014/9/24.
 */
public class UserBean implements IClusterable {
    @NotNull(message = "'企业名称'是必填项")
    String qymc;//企业名称
    @NotNull(message = "'成立时间'是必填项")
    Date clsj;//成立时间
    @NotNull(message = "'法人代表姓名'是必填项")
    String frdb;//法人代表姓名
    @NotNull(message = "'从业人数'是必填项")
    long cyrs;//从业人数
    @NotNull(message = "'注册地址'是必填项")
    String addresszc;//注册地址
    @NotNull(message = "'经营地址'是必填项")
    String addressjy;//经营地址
    @NotNull(message = "'邮编'是必填项")
    String postcode;//邮编
    @NotNull(message = "'企业联系方式'是必填项")
    String qylxfs;//企业联系方式
    @NotNull(message = "'电子邮箱'是必填项")
    String mailaddress;//电子邮箱

    @NotNull(message = "'姓名'是必填项")
    private String name;  // 姓名
    @NotNull(message = "'登录名'是必填项")
    private String loginName;// 登录名
    @NotNull(message = "'密码'是必填项")
    private String password; // 密码
    @NotNull(message = "'确认密码'是必填项")
    private String confirmPassored;  //确认密码
    @NotNull(message = "'手机号'是必填项")
    @Pattern(regexp = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)", message = "手机格式不正确")
    private String mobile;    // 手机

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    public String getFrdb() {
        return frdb;
    }

    public void setFrdb(String frdb) {
        this.frdb = frdb;
    }

    public long getCyrs() {
        return cyrs;
    }

    public void setCyrs(long cyrs) {
        this.cyrs = cyrs;
    }

    public String getAddresszc() {
        return addresszc;
    }

    public void setAddresszc(String addresszc) {
        this.addresszc = addresszc;
    }

    public String getAddressjy() {
        return addressjy;
    }

    public void setAddressjy(String addressjy) {
        this.addressjy = addressjy;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getQylxfs() {
        return qylxfs;
    }

    public void setQylxfs(String qylxfs) {
        this.qylxfs = qylxfs;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassored() {
        return confirmPassored;
    }

    public void setConfirmPassored(String confirmPassored) {
        this.confirmPassored = confirmPassored;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}