package com.redtourism.demo.service.impl;


import com.google.common.collect.Lists;
import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.dao.ActivityJoinMapper;
import com.redtourism.demo.dao.ActivityMapper;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.util.PropertiesUtil;
import com.redtourism.demo.vo.ActivityJoinItemVo;
import com.redtourism.demo.vo.ActivityJoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2021-1-16 - 16:45
 * Created by Salmon
 */
@Service("iJoinService")
public class IJoinServiceImpl implements IJoinService {

    @Autowired
    private ActivityJoinMapper activityJoinMapper;

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 用户直接加入活动
     * @param userId
     * @param activityId
     * @return
     */
    public ServerResponse addActivity(Integer userId,Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);


        if(activityJoin == null){
            if(activity.getActivityPeople() - activity.getJoinpeople() < 0){
                return ServerResponse.createByErrorMessage("此活动报名人数已满");
            }else {
                ActivityJoin activityJoinItem = new ActivityJoin();
                activityJoinItem.setAid(activityId);
                activityJoinItem.setQuantity(activity.getActivityPeople() - activity.getJoinpeople());
                activityJoinItem.setUserId(userId);
                activityJoinMapper.insert(activityJoinItem);
                //更新可加入的活动人数
                activityMapper.addJoinpeopleByPrimaryKey(activityId);
            }

        }else{
            return ServerResponse.createByErrorMessage("您已经加入过此活动，请不要重复加入");
        }
        return ServerResponse.createBySuccessMessage("您已成功加入此活动");

    }

    /**
     * 退出当前活动
     * @param userId
     * @param activityId
     * @return
     */
    public ServerResponse quitActivity(Integer userId,Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        Activity activity = activityMapper.selectByPrimaryKey(activityId);


        if(activityJoin != null){
                activityJoinMapper.deleteByPrimaryKey(activityJoin.getId());
                //更新可加入的活动人数
                activityMapper.reduceJoinpeopleByPrimaryKey(activityId);
        }else{
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
        }
        return ServerResponse.createBySuccessMessage("您已成功退出此活动");
    }


































    /**
     * -------------------------------------------------------
     * 等开发到收藏夹再使用下面的代码
     */

    /**
     * 用户根据收藏活动中的活动id加入该活动
     * @param userId
     * @param activityId
     * @return
     */
    public ServerResponse<ActivityJoinVo> add(Integer userId, Integer activityId){
        if (activityId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        ActivityJoin activityJoin = activityJoinMapper.selectByUserIdActivityId(userId, activityId);
        if(activityJoin == null){
            //当前用户没有加入过该活动，需要重新设置
            ActivityJoin activityJoinItem = new ActivityJoin();
            activityJoinItem.setChecked(Const.MyActivity.CHECKED); //设置活动被选中，此功能看心情
            activityJoinItem.setAid(activityId);
            activityJoinItem.setUserId(userId);
            activityJoinMapper.insert(activityJoinItem);
        }else{
            return ServerResponse.createByErrorMessage("您已经加入过此活动，请不要重复加入");
        }

        return this.list(userId);
    }





    /**
     * 展示收藏的活动列表
     * @param userId
     * @return
     */
    public ServerResponse<ActivityJoinVo> list (Integer userId){
        ActivityJoinVo activityJoinVo = this.getActivityJoinVoLimit(userId);
        if(activityJoinVo == null){
            return ServerResponse.createByErrorMessage("此活动不存在");
        }else {
            return ServerResponse.createBySuccess(activityJoinVo);
        }
    }

    private ActivityJoinVo getActivityJoinVoLimit(Integer userId){
        ActivityJoinVo activityJoinVo = new ActivityJoinVo();

        //获得所有的red_tourism_activity_join表信息
        List<ActivityJoin> activityJoinList = activityJoinMapper.selectByUserId(userId);
        List<ActivityJoinItemVo> activityJoinItemVoList = Lists.newArrayList();


        if(CollectionUtils.isNotEmpty(activityJoinList)){
            for(ActivityJoin activityJoinItem : activityJoinList){
                ActivityJoinItemVo activityJoinItemVo = new ActivityJoinItemVo();


                activityJoinItemVo.setId(activityJoinItem.getId());
                activityJoinItemVo.setUserId(userId);
                activityJoinItemVo.setAid(activityJoinItem.getAid());
                //获得所有的活动red_tourism_activity表信息
                Activity activity = activityMapper.selectByPrimaryKey(activityJoinItem.getAid());
                if(activity != null){
                    activityJoinItemVo.setMainPicture(activity.getMainPicture());
                    activityJoinItemVo.setActivityPeople(activity.getActivityPeople());
                    activityJoinItemVo.setActivityTitle(activity.getActivityTitle());
                    activityJoinItemVo.setActivityContent(activity.getActivityContent());
                    activityJoinItemVo.setActivityType(activity.getActivityType());
                    activityJoinItemVo.setActivityAddress(activity.getActivityAddress());
                    activityJoinItemVo.setActivityStatus(activity.getActivityStatus());
                    activityJoinItemVo.setJoinPeople(activity.getJoinpeople() + 1);
                    //判断当前活动是否满员
                    int LimitCount = 0;
                    if(activity.getActivityPeople() >= activity.getJoinpeople()){
                        //活动可参与人数充足的时候
                        LimitCount = activityJoinItem.getQuantity();
                        activityJoinItemVo.setLimitQuantity(Const.MyActivity.LIMIT_NUM_SUCCESS);
                    }else{
                        LimitCount = activity.getActivityPeople() - activity.getJoinpeople();
                        activityJoinItemVo.setLimitQuantity(Const.MyActivity.LIMIT_NUM_FAIL);
                        //收藏的活动中更新有效库存
                        ActivityJoin activityJoinForQuantity = new ActivityJoin();
                        activityJoinForQuantity.setId(activityJoinItem.getId());
                        activityJoinForQuantity.setQuantity(LimitCount);
                        activityJoinMapper.updateByPrimaryKeySelective(activityJoinForQuantity);
                    }
                    activityJoinItemVo.setQuantity(LimitCount);

                    activityJoinItemVo.setActivityChecked(activityJoinItem.getChecked());
                }

                activityJoinItemVoList.add(activityJoinItemVo);
            }
        }
        activityJoinVo.setActivityJoinItemVoList(activityJoinItemVoList);
        activityJoinVo.setAllChecked(this.getAllCheckedStatus(userId));
        activityJoinVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return activityJoinVo;
    }


    /**
     * 查看是否为全选
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }

        //此查询查询的是：当userId确定时未勾选的收藏列表中活动个数，所以为0时全选，不为0时不是全选
        return activityJoinMapper.selectActivityCheckedStatusByUserId(userId) == 0;
    }






}
