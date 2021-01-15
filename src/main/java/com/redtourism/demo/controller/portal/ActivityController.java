package com.redtourism.demo.controller.portal;

import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.Activity;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.IActivityService;
import com.redtourism.demo.service.IFileService;
import com.redtourism.demo.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2021-1-15 - 17:50
 * Created by Salmon
 */
@Controller
@RequestMapping("/activity/")
public class ActivityController {

    @Autowired
    private IFileService iFileService;

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IUserService iUserService;

    /**
     * 创建或更新活动
     * @param session
     * @param activity
     * @return
     */
    @RequestMapping("save_activity.do")
    @ResponseBody
    public ServerResponse saveActivity(HttpSession session, Activity activity){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }else{
            //增加产品的业务逻辑
            return iActivityService.saveOrUpdateActivity(activity);
        }
    }

    /**
     * 以活动id为依据设置活动状态
     * @param session
     * @param activityId
     * @param status
     * @return
     */
    @RequestMapping("set_activity_status.do")
    @ResponseBody
    public ServerResponse setActivityStatus(HttpSession session, Integer activityId, Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }else{
            //增加产品的业务逻辑
            return iActivityService.setActivityStatus(activityId,status);
        }
    }

    //根据活动类别分页查询
    //获取活动列表
    //图片上传
    //获取报名人数

    //点赞数查询
    //点赞功能
    //评论功能(只能评论不能回复)


    //用户参加活动
    //用户退出活动
    //根据活动号查询报名人联系信息

    /**
     * 获取活动详细信息,包含
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }else{
            //增加产品的业务逻辑
            return iActivityService.getActivityDetail(activityId);
        }
    }




}
