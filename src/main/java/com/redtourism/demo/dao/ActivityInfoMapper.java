package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.ActivityInfo;

public interface ActivityInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActivityInfo record);

    int insertSelective(ActivityInfo record);

    ActivityInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivityInfo record);

    int updateByPrimaryKey(ActivityInfo record);
}