package com.redtourism.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.util.DateTimeUtil;
import com.redtourism.demo.util.PropertiesUtil;
import com.redtourism.demo.vo.ActivityDetailVo;
import com.redtourism.demo.vo.ActivityListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021-1-15 - 17:54
 * Created by Salmon
 */
@Service("iActivityService")
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;



    /**
     * 发布或更新活动
     * @param activity
     * @return
     */
    public ServerResponse saveOrUpdateActivity(Activity activity){
        if(activity != null){
            if(activity.getAid() != null){
                int rowCount = activityMapper.updateByPrimaryKey(activity);
                if(rowCount > 0){
                    return ServerResponse.createBySuccessMessage("活动更新成功");
                }
                return ServerResponse.createByErrorMessage("更新活动失败");
            }else {
                int rowCount = activityMapper.insert(activity);
                if(rowCount > 0 && activity.getActivityPeople() > activity.getJoinpeople()) {
                    return ServerResponse.createBySuccessMessage("创建活动成功");
                }
                return ServerResponse.createByErrorMessage("创建活动失败");
            }
        }

        return ServerResponse.createByErrorMessage("活动创建不正确");
    }

    /**
     * 以活动id为依据修改活动状态
     * @param activityId
     * @param status
     * @return
     */
    public ServerResponse<String> setActivityStatus(Integer activityId,Integer status){
        if(activityId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Activity activity = new Activity();
        activity.setAid(activityId);
        activity.setActivityStatus(status);
        int rowCount = activityMapper.updateByPrimaryKeySelective(activity);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改活动状态成功");
        }
        return ServerResponse.createByErrorMessage("修改活动状态失败");
    }


    /**
     * 获取活动详情
     * @param activityId
     * @return
     */
    public ServerResponse<ActivityDetailVo> getActivityDetail(Integer activityId){
        if(activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Activity activity = activityMapper.selectByPrimaryKey(activityId);
        if(activity == null){
            return ServerResponse.createByErrorMessage("活动已取消或删除");
        }
        if(activity.getActivityStatus() != Const.ActivityStatusEnum.ON_PROCEED.getCode()){
            return ServerResponse.createByErrorMessage("活动已取消或者删除");
        }
        ActivityDetailVo productDetailVo = assembleProductDetailVo(activity);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 为正上方getActivityDetail方法服务
     * @param activity
     * @return
     */
    private ActivityDetailVo assembleProductDetailVo(Activity activity){
        ActivityDetailVo activityDetailVo = new ActivityDetailVo();
        activityDetailVo.setAid(activity.getAid());
        activityDetailVo.setUserId(activity.getUserId());
        activityDetailVo.setMainPicture(activity.getMainPicture());
        activityDetailVo.setActivityPeople(activity.getActivityPeople());
        activityDetailVo.setActivityContent(activity.getActivityContent());
        activityDetailVo.setActivityStatus(activity.getActivityStatus());
        activityDetailVo.setActivityType(activity.getActivityType());
        activityDetailVo.setActivityAddress(activity.getActivityAddress());
        activityDetailVo.setJoinPeople(activity.getJoinpeople());
        //添加需要的host地址，就是你购买的网址或公网ip,在redTourism,properties里配置
        activityDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happyrmmall.top/"));

        activityDetailVo.setCreateTime(DateTimeUtil.dateToStr(activity.getCreateTime()));
        activityDetailVo.setUpdateTime(DateTimeUtil.dateToStr(activity.getUpdateTime()));
        return activityDetailVo;
    }

    /**
     * 活动分页列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse getActivityList(int pageNum,int pageSize){

        PageHelper.startPage(pageNum,pageSize);
        List<Activity> activityList = activityMapper.selectList();


        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activityItem : activityList){
            ActivityListVo activityListVo = assembleActivityListVo(activityItem);
            activityListVoList.add(activityListVo);
        }
        PageInfo pageResult = new PageInfo(activityList);
        pageResult.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ActivityListVo assembleActivityListVo(Activity activity){
        ActivityListVo activityListVo = new ActivityListVo();
        activityListVo.setAid(activity.getAid());
        activityListVo.setMainPicture(activity.getMainPicture());
        activityListVo.setActivityPeople(activity.getActivityPeople());
        activityListVo.setActivityTitle(activity.getActivityTitle());
        activityListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happyrmmall.top/"));
        activityListVo.setActivityType(activity.getActivityType());
        activityListVo.setActivityAddress(activity.getActivityAddress());
        activityListVo.setActivityStatus(activity.getActivityStatus());

        activityListVo.setJoinPeople(activity.getJoinpeople());

        return activityListVo;
    }

    /**
     * 模糊查询以及分类查询
     * @param keyword
     * @param type
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    public ServerResponse<PageInfo> getActivityByKeywordType(String keyword,Integer type,int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword) && type == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (type != null){
            Activity activityType = activityMapper.selectByType(type);
            if(activityType == null && StringUtils.isBlank(keyword)){
                //没有该分类，且没有关键字，这时候返回一个空的结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ActivityListVo> activityListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(activityListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
        }
        if(StringUtils.isNotBlank(keyword)){
            //模糊查询
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ActivityListOrderBy.PEOPLE_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        //模糊查询名称及种类id
        List<Activity> activityList = activityMapper.selectByNameAndType(StringUtils.isBlank(keyword)?null:keyword, type == null?null:type);

        List<ActivityListVo> activityListVoList = Lists.newArrayList();
        for (Activity activity : activityList){
            ActivityListVo activityListVo = assembleActivityListVo(activity);
            activityListVoList.add(activityListVo);
        }

        PageInfo pageInfo = new PageInfo(activityList);
        pageInfo.setList(activityListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }











}
