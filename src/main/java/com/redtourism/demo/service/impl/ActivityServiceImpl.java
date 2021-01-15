package com.redtourism.demo.service.impl;

import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.util.DateTimeUtil;
import com.redtourism.demo.util.PropertiesUtil;
import com.redtourism.demo.vo.ActivityDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
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
        ActivityDetailVo productDetailVo = assembleProductDetailVo(activity);
        return ServerResponse.createBySuccess(productDetailVo);
    }

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

        activityDetailVo.setImageHost(PropertiesUtil.getProperty("http://img.happymmall.com/", "http://img.happymmall.com/"));

        activityDetailVo.setCreateTime(DateTimeUtil.dateToStr(activity.getCreateTime()));
        activityDetailVo.setUpdateTime(DateTimeUtil.dateToStr(activity.getUpdateTime()));
        return activityDetailVo;
    }



}
