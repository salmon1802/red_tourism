package com.redtourism.demo.service;

import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.vo.ActivityJoinVo;

/**
 * @date 2021-1-16 - 16:45
 * Created by Salmon
 */
public interface IJoinService {

    ServerResponse<ActivityJoinVo> add(Integer userId, Integer activityId);

    ServerResponse addActivity(Integer userId,Integer activityId);

    ServerResponse quitActivity(Integer userId,Integer activityId);


}
