package com.redtourism.demo.service;

import com.redtourism.demo.common.ServerResponse;

/**
 * @date 2021-1-16 - 21:21
 * Created by Salmon
 */
public interface IActivityInfoService {

    ServerResponse Point(Integer userId, Integer activityId);

    ServerResponse pointCount(Integer activityId);

}
