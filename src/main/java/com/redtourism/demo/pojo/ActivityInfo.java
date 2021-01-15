package com.redtourism.demo.pojo;

import java.util.Date;

public class ActivityInfo {
    private Long id;

    private Long articleId;

    private Long userId;

    private String content;

    private Long pointTotal;

    private Date createTime;

    private Date updateTime;

    public ActivityInfo(Long id, Long articleId, Long userId, String content, Long pointTotal, Date createTime, Date updateTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.pointTotal = pointTotal;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ActivityInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(Long pointTotal) {
        this.pointTotal = pointTotal;
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