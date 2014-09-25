package com.daren.operations.entities;

import com.daren.core.api.persistence.WorkflowEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;

/**
 * @类描述：特种作业人员操作资格证
 * @创建人：张清欣
 * @创建时间：2014-09-09 下午14:50
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
@Entity
@Table(name = "gov_safety_operations")
@Inheritance(strategy = InheritanceType.JOINED)
public class OperationsBean extends WorkflowEntity {
    private String name;//姓名
    private String phone;//电话
    private String workType;//作业类别
    private String operationProject;//准操项目
    private String enterpriseName;//企业名称

    private String code;//证书编号
    private Date receiveDate;//初领日期
    private Date startDate;//有效期限开始
    private Date endDate;//有效期限结束
    private Date reviewDate;//复审日期

    private String linkHandle;//当前办理环节
    private String loginName;//申请人登陆名
    private long proposerId;//审请人ID
    private long enterpriseId;//企业ID

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getOperationProject() {
        return operationProject;
    }

    public void setOperationProject(String operationProject) {
        this.operationProject = operationProject;
    }

    public long getProposerId() {
        return proposerId;
    }

    public void setProposerId(long proposerId) {
        this.proposerId = proposerId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getLinkHandle() {
        return linkHandle;
    }

    public void setLinkHandle(String linkHandle) {
        this.linkHandle = linkHandle;
    }
}
