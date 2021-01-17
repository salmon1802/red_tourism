package com.redtourism.demo.service.impl;

import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityInfoMapper;
import com.redtourism.demo.pojo.ActivityInfo;
import com.redtourism.demo.service.IActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2021-1-16 - 21:22
 * Created by Salmon
 */
@Service("iActivityInfoService")
public class ActivityInfoServiceImpl implements IActivityInfoService {


    @Autowired
    private ActivityInfoMapper activityInfoMapper;


    /**
     * 点赞和取消点赞
     * @param userId
     * @param activityId
     * @return
     */
    public ServerResponse Point(Integer userId, Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ActivityInfo activityJoin = activityInfoMapper.selectByUserIdActivityId(userId, activityId);
        if(activityJoin == null){
            //此用户没有对此活动点赞或评论
            ActivityInfo activityInfo = new ActivityInfo();
            activityInfo.setAid(activityId);
            activityInfo.setUserId(userId);
            activityInfo.setpoint(1);
            activityInfoMapper.insert(activityInfo);
        }else{
            //如果点赞了那就取消
            if(activityJoin.getpoint() == 1) {
                activityInfoMapper.cancelPointByUserIdActivityId(userId, activityId);
                return ServerResponse.createBySuccessMessage("您已经取消点赞");
            }else {
                //只评论没点赞，那就点赞
                activityInfoMapper.pointByUserIdActivityId(userId, activityId);
            }
        }
        return ServerResponse.createBySuccessMessage("您已经点赞");
    }

    /**
     * 获取点赞数量
     * @param activityId
     * @return
     */
    public ServerResponse pointCount(Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        int pointCount = activityInfoMapper.selectPointCountByActivityId(activityId);

        return ServerResponse.createBySuccess(pointCount);
    }


}
