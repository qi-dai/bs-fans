package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.request.AttentionRequest;
import com.eden.fans.bs.domain.request.QryFromAttRequest;
import com.eden.fans.bs.domain.request.QryToAttRequest;
import com.eden.fans.bs.domain.request.ResetPwdRequest;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.IUserAttentionService;
import com.eden.fans.bs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/3/20.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserAttentionService userAttentionService;


    @RequestMapping(value = "/qryUserInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String qryUserInfo(UserVo userVo) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.qryUserInfo(userVo.getUserCode()));
    }

    @RequestMapping(value = "/updateUserInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUserInfo(UserVo userVo) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.updateUserInfo(userVo));
    }

    @RequestMapping(value = "/resetPwd", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String resetPwd(@ReqCheckParam ResetPwdRequest resetPwdRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.resetPwd(resetPwdRequest));
    }

    /**
     * 关注/取消关注用户
     * */
    @RequestMapping(value = "/setAttention", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String attention(@ReqCheckParam AttentionRequest attentionRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userAttentionService.setAttention(attentionRequest));
    }


    @RequestMapping(value = "/getFromAttentions", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getFromAttentions(@ReqCheckParam QryFromAttRequest qryFromAttRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userAttentionService.getFromAttentions(qryFromAttRequest));
    }

    @RequestMapping(value = "/getToAttentions", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getToAttentions(@ReqCheckParam QryToAttRequest qryToAttRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userAttentionService.getToAttentions(qryToAttRequest));
    }

}
