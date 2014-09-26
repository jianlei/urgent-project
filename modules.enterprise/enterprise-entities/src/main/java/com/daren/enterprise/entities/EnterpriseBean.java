package com.daren.enterprise.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @类描述：企业实体类
 * @创建人：sunlf
 * @创建时间：2014-05-14 下午1:49
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "urg_ent_enterprise")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class EnterpriseBean extends PersistentEntity {

    @NotNull(message = "'企业代码'是必填项")
    String qyid;//企业id
    @NotNull(message = "'企业名称'是必填项")
    String qymc;//企业名称
    Date clsj;//成立时间
    String frdb;//法人代表姓名
    String zzjgdm;//组织机构代码
    String sdlx;//隶属关系代码，参照t_dm_gy_lsgx
    String jjlxbm;//登记注册类型编码，参照t_dm_gy_jjlx
    long cyrs;//从业人数
    long zcaqgcsrs;//注册安全工程师人数
    long aqrs;//专职安全管理人数
    long jzaqrs;//兼职安全管理人员数
    String hymldm;//行业门类代码，参照t_dm_gy_hyml
    String hylbbm;//行业大类代码，参照t_dm_gy_hydl
    String hyzxldm;//行业中小类代码，参照t_dm_gy_hyzxl
    String xjqybj;//企业是否有下级单位（1：是；0：否）
    String sjqyid;//上级单位id
    String qygmbm;//企业规模代码，参照t_dm_gy_qygm
    String gmqk;//规模情况（是否规模以上企业，0否，1是）
    long zczc;//注册资产(万元)
    String jgfl;//监管分类，参照t_dm_yh_jgfl
    double sndxssr;//上年度销售收入（万元）
    double aqscfy;//安全生产费用（万元）
    String yazlqybj;//液氨制冷企业标记1是，0否
    String xzqhsheng;//行政区划省，参照t_dm_gy_xzqhszxs
    String xzqhds;//行政区划地市，参照t_dm_gy_xzqhds
    String xzqhxq;//行政区划区县，参照t_dm_gy_xzqhxq
    String addresszc;//注册地址
    String addressjy;//经营地址
    String postcode;//邮编
    String qylxfs;//企业联系方式
    String mailaddress;//电子邮箱
    String dlwz;//地理位置（经度+维度）
    String jd;//地理位置（经度）
    String wd;//地理位置（维度）
    String zdxfdwbj;//重点消防单位标记，0否，1是
    String zybbj;//是否有职业危害因素,1是，0否
    String aqjgszqk;//是否有专门安全管理部门，1是，0否
    String zyjyxm;//生产经营内容
    String zyyl;//主要原料
    String zycp;//主要产品
    String jgjgdm;//监管机构代码
    String lrryjg;//开户人员所在部门或科室代码
    String username;//企业登录用户名
    String userpwd;//企业登录密码（md5）
    String zfbj;//作废标记，1为作废，0为启用
    String isMark;//孙平加的不知道干啥的

    public EnterpriseBean() {
        setCreationDate(new Date());
        qyid = "";
        qymc = "";
        clsj = new Date();
        frdb = "";
        zzjgdm = "";
        sdlx = "";
        jjlxbm = "";
        cyrs = 0;
        zcaqgcsrs = 0;
        aqrs = 0;
        jzaqrs = 0;
        hymldm = "";
        hylbbm = "";
        hyzxldm = "";
        xjqybj = "";
        sjqyid = "";
        qygmbm = "";
        gmqk = "";
        zczc = 0;
        jgfl = "";
        sndxssr = 0;
        aqscfy = 0;
        yazlqybj = "";
        xzqhsheng = "";
        xzqhds = "";
        xzqhxq = "";
        addresszc = "";
        addressjy = "";
        postcode = "";
        qylxfs = "";
        mailaddress = "";
        dlwz = "";
        jd = "";
        wd = "";
        zdxfdwbj = "";
        zybbj = "";
        aqjgszqk = "";
        zyjyxm = "";
        zyyl = "";
        zycp = "";
        jgjgdm = "";
        lrryjg = "";
        username = "";
        userpwd = "";
        zfbj = "";
        isMark = "";
    }

    public String getIsMark() {
        return isMark;
    }

    public void setIsMark(String isMark) {
        this.isMark = isMark;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

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

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public String getSdlx() {
        return sdlx;
    }

    public void setSdlx(String sdlx) {
        this.sdlx = sdlx;
    }

    public String getJjlxbm() {
        return jjlxbm;
    }

    public void setJjlxbm(String jjlxbm) {
        this.jjlxbm = jjlxbm;
    }

    public long getCyrs() {
        return cyrs;
    }

    public void setCyrs(long cyrs) {
        this.cyrs = cyrs;
    }

    public long getZcaqgcsrs() {
        return zcaqgcsrs;
    }

    public void setZcaqgcsrs(long zcaqgcsrs) {
        this.zcaqgcsrs = zcaqgcsrs;
    }

    public long getAqrs() {
        return aqrs;
    }

    public void setAqrs(long aqrs) {
        this.aqrs = aqrs;
    }

    public long getJzaqrs() {
        return jzaqrs;
    }

    public void setJzaqrs(long jzaqrs) {
        this.jzaqrs = jzaqrs;
    }

    public String getHymldm() {
        return hymldm;
    }

    public void setHymldm(String hymldm) {
        this.hymldm = hymldm;
    }

    public String getHylbbm() {
        return hylbbm;
    }

    public void setHylbbm(String hylbbm) {
        this.hylbbm = hylbbm;
    }

    public String getHyzxldm() {
        return hyzxldm;
    }

    public void setHyzxldm(String hyzxldm) {
        this.hyzxldm = hyzxldm;
    }

    public String getXjqybj() {
        return xjqybj;
    }

    public void setXjqybj(String xjqybj) {
        this.xjqybj = xjqybj;
    }

    public String getSjqyid() {
        return sjqyid;
    }

    public void setSjqyid(String sjqyid) {
        this.sjqyid = sjqyid;
    }

    public String getQygmbm() {
        return qygmbm;
    }

    public void setQygmbm(String qygmbm) {
        this.qygmbm = qygmbm;
    }

    public String getGmqk() {
        return gmqk;
    }

    public void setGmqk(String gmqk) {
        this.gmqk = gmqk;
    }

    public long getZczc() {
        return zczc;
    }

    public void setZczc(long zczc) {
        this.zczc = zczc;
    }

    public String getJgfl() {
        return jgfl;
    }

    public void setJgfl(String jgfl) {
        this.jgfl = jgfl;
    }

    public double getSndxssr() {
        return sndxssr;
    }

    public void setSndxssr(double sndxssr) {
        this.sndxssr = sndxssr;
    }

    public double getAqscfy() {
        return aqscfy;
    }

    public void setAqscfy(double aqscfy) {
        this.aqscfy = aqscfy;
    }

    public String getYazlqybj() {
        return yazlqybj;
    }

    public void setYazlqybj(String yazlqybj) {
        this.yazlqybj = yazlqybj;
    }

    public String getXzqhsheng() {
        return xzqhsheng;
    }

    public void setXzqhsheng(String xzqhsheng) {
        this.xzqhsheng = xzqhsheng;
    }

    public String getXzqhds() {
        return xzqhds;
    }

    public void setXzqhds(String xzqhds) {
        this.xzqhds = xzqhds;
    }

    public String getXzqhxq() {
        return xzqhxq;
    }

    public void setXzqhxq(String xzqhxq) {
        this.xzqhxq = xzqhxq;
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

    public String getDlwz() {
        return dlwz;
    }

    public void setDlwz(String dlwz) {
        this.dlwz = dlwz;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getZdxfdwbj() {
        return zdxfdwbj;
    }

    public void setZdxfdwbj(String zdxfdwbj) {
        this.zdxfdwbj = zdxfdwbj;
    }

    public String getZybbj() {
        return zybbj;
    }

    public void setZybbj(String zybbj) {
        this.zybbj = zybbj;
    }

    public String getAqjgszqk() {
        return aqjgszqk;
    }

    public void setAqjgszqk(String aqjgszqk) {
        this.aqjgszqk = aqjgszqk;
    }

    public String getZyjyxm() {
        return zyjyxm;
    }

    public void setZyjyxm(String zyjyxm) {
        this.zyjyxm = zyjyxm;
    }

    public String getZyyl() {
        return zyyl;
    }

    public void setZyyl(String zyyl) {
        this.zyyl = zyyl;
    }

    public String getZycp() {
        return zycp;
    }

    public void setZycp(String zycp) {
        this.zycp = zycp;
    }

    public String getJgjgdm() {
        return jgjgdm;
    }

    public void setJgjgdm(String jgjgdm) {
        this.jgjgdm = jgjgdm;
    }

    public String getLrryjg() {
        return lrryjg;
    }

    public void setLrryjg(String lrryjg) {
        this.lrryjg = lrryjg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getZfbj() {
        return zfbj;
    }

    public void setZfbj(String zfbj) {
        this.zfbj = zfbj;
    }
}
