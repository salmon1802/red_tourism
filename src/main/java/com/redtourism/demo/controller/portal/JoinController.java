package com.redtourism.demo.controller.portal;

import com.redtourism.demo.common.Const;
import com.redtourism.demo.common.ResponseCode;
import com.redtourism.demo.common.ServerResponse;
import com.redtourism.demo.pojo.ActivityJoin;
import com.redtourism.demo.pojo.User;
import com.redtourism.demo.service.IJoinService;
import com.redtourism.demo.vo.ActivityJoinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @date 2021-1-16 - 16:34
 * Created by Salmon
 */
@Controller
@RequestMapping("/join/")
public class JoinController {


    @Autowired
    private IJoinService iJoinService;




    /**
     * 用户直接加入活动
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("add_activity.do")
    @ResponseBody
    public ServerResponse<ActivityJoinVo> addActivity(HttpSession session,Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iJoinService.addActivity(user.getId(), activityId);
    }

    /**
     * 用户直接退出活动
     * @param session
     * @param activityId
     * @return
     */
    @RequestMapping("quit_activity.do")
    @ResponseBody
    public ServerResponse<ActivityJoinVo> quitActivity(HttpSession session,Integer activityId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iJoinService.quitActivity(user.getId(), activityId);
    }



















    /**
     * 收藏夹功能我溜了
     */


    /**
     *
     * 用户通过关注列表参加活动
     * @param session
     * @param activityId
     * @return

     @RequestMapping("add.do")
     @ResponseBody
     public ServerResponse<ActivityJoinVo> add(HttpSession session, Integer activityId){
     User user = (User)session.getAttribute(Const.CURRENT_USER);
     if (user == null){
     return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
     }
     return iJoinService.add(user.getId(), activityId);
     }
     */

}
