package com.redtourism.demo.pojo;

import java.util.Date;

public class Activity {
    private Integer aid;

    private Integer userId;

    private String mainPicture;

    private Integer activityPeople;

    private String activityTitle;

    private String activityContent;

    private Integer activityStatus;

    private Integer activityType;

    private String activityAddress;

    private Integer joinpeople;

    private Date createTime;

    private Date updateTime;

    public Activity(Integer aid, Integer userId, String mainPicture, Integer activityPeople, String activityTitle, String activityContent, Integer activityStatus, Integer activityType, String activityAddress, Integer joinpeople, Date createTime, Date updateTime) {
        this.aid = aid;
        this.userId = userId;
        this.mainPicture = mainPicture;
        this.activityPeople = activityPeople;
        this.activityTitle = activityTitle;
        this.activityContent = activityContent;
        this.activityStatus = activityStatus;
        this.activityType = activityType;
        this.activityAddress = activityAddress;
        this.joinpeople = joinpeople;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Activity() {
        super();
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(String mainPicture) {
        this.mainPicture = mainPicture == null ? null : mainPicture.trim();
    }

    public Integer getActivityPeople() {
        return activityPeople;
    }

    public void setActivityPeople(Integer activityPeople) {
        this.activityPeople = activityPeople;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle == null ? null : activityTitle.trim();
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent == null ? null : activityContent.trim();
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress == null ? null : activityAddress.trim();
    }

    public Integer getJoinpeople() {
        return joinpeople;
    }

    public void setJoinpeople(Integer joinpeople) {
        this.joinpeople = joinpeople;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}