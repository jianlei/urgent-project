package com.daren.reserveplan.entities;

import com.daren.core.api.persistence.PersistentEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by dell on 14-1-16.
 */
@Entity
@Table(name = "biz_plan_reserve")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class ReservePlanBean extends PersistentEntity {

    private String name;//预案名称
    private String description;//描述
    private String mark;//标识
    private String level;//级别
    private String type;//类型
    private String approveType;//审批状态
    private Date approveTime;//审批时间

    private String reservePlanApplyId;//应急预案申请表文档ID
    private String reservePlanRegisterId;//应急预案注册表文档ID
    private String reviewCommentId;//评审意见文档ID
    private String reviewExpertId;//评审意见文档ID
    private String reservePlanDocumentId;//应急预案文档原件ID
    private List expertList;//专家列表
    private List<SpotPlanBean> spotPlanBeanList;//现场预案集合
    private List<SpecialPlanBean> specialPlanBeanList;//专项预案集合
    private String comprehensivePlanId;//综合预案文档ID

    public ReservePlanBean() {
        name = "";
        description = "";
        mark = "";
        level = "";
        approveType = "审批通过";
        type = "";
        approveTime = new Date();
        reservePlanApplyId = "";
        reservePlanRegisterId = "";
        reviewCommentId = "";
        reservePlanDocumentId = "";
        comprehensivePlanId = "";
        reviewExpertId = "";
        expertList = new ArrayList();
        spotPlanBeanList = new ArrayList<>();
        specialPlanBeanList = new ArrayList<>();
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReviewExpertId() {
        return reviewExpertId;
    }

    public void setReviewExpertId(String reviewExpertId) {
        this.reviewExpertId = reviewExpertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReservePlanApplyId() {
        return reservePlanApplyId;
    }

    public void setReservePlanApplyId(String reservePlanApplyId) {
        this.reservePlanApplyId = reservePlanApplyId;
    }

    public List getExpertList() {
        return expertList;
    }

    public void setExpertList(List expertList) {
        this.expertList = expertList;
    }

    public List<SpotPlanBean> getSpotPlanBeanList() {
        return spotPlanBeanList;
    }

    public void setSpotPlanBeanList(List<SpotPlanBean> spotPlanBeanList) {
        this.spotPlanBeanList = spotPlanBeanList;
    }

    public List<SpecialPlanBean> getSpecialPlanBeanList() {
        return specialPlanBeanList;
    }

    public void setSpecialPlanBeanList(List<SpecialPlanBean> specialPlanBeanList) {
        this.specialPlanBeanList = specialPlanBeanList;
    }

    public String getReservePlanRegisterId() {
        return reservePlanRegisterId;
    }

    public void setReservePlanRegisterId(String reservePlanRegisterId) {
        this.reservePlanRegisterId = reservePlanRegisterId;
    }

    public String getReviewCommentId() {
        return reviewCommentId;
    }

    public void setReviewCommentId(String reviewCommentId) {
        this.reviewCommentId = reviewCommentId;
    }

    public String getReservePlanDocumentId() {
        return reservePlanDocumentId;
    }

    public void setReservePlanDocumentId(String reservePlanDocumentId) {
        this.reservePlanDocumentId = reservePlanDocumentId;
    }

    public String getComprehensivePlanId() {
        return comprehensivePlanId;
    }

    public void setComprehensivePlanId(String comprehensivePlanId) {
        this.comprehensivePlanId = comprehensivePlanId;
    }
}
