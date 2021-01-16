package com.redtourism.demo.controller.portal;

import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.IActivityInfoService;
import com.redtourism.demo.vo.ActivityJoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @date 2021-1-16 - 16:32
 * Created by Salmon
 */
@Controller
@RequestMapping("/interaction/")
public class InteractionController {

    @Autowired
    private IActivityInfoService iActivityInfoService;

    //点赞数查询
    //评论分页显示功能
    //评论功能(只能评论不能回复)

    /**
     * 点赞和取消点赞
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("point.do")
    @ResponseBody
    public ServerResponse<ActivityJoinVo> Point(HttpSession session, Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iActivityInfoService.Point(user.getId(), activityId);
    }






}
