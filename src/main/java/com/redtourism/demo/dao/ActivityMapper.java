package com.redtourism.demo.dao;

import com.redtourism.demo.pojo.Activity;

public interface ActivityMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer aid);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);
}