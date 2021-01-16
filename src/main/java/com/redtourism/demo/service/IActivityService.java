package com.redtourism.demo.service;

import com.github.pagehelper.PageInfo;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.vo.ActivityDetailVo;

/**
 * @date 2021-1-15 - 17:52
 * Created by Salmon
 */
public interface IActivityService {

     ServerResponse saveOrUpdateActivity(Activity activity);

     ServerResponse<String> setActivityStatus(Integer activityId,Integer status);

     ServerResponse<ActivityDetailVo> getActivityDetail(Integer activityId);

     ServerResponse getActivityList(int pageNum,int pageSize);

     ServerResponse<PageInfo> getActivityByKeywordType(String keyword, Integer type, int pageNum, int pageSize, String orderBy);

}
