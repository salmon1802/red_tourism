package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.ActivityJoin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityJoinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityJoin record);

    int insertSelective(ActivityJoin record);

    ActivityJoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityJoin record);

    int updateByPrimaryKey(ActivityJoin record);

    ActivityJoin selectByUserIdActivityId(@Param("userId")Integer userId, @Param("activityId") Integer activityId);

    List<ActivityJoin> selectByUserId(Integer userId);

    int selectActivityCheckedStatusByUserId(Integer userId);
}