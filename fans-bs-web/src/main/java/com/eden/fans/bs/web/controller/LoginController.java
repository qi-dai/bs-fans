package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.request.LoginRequest;
import com.eden.fans.bs.domain.request.RegisterRequest;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.ICommonService;
import com.eden.fans.bs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 2016/3/15.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ICommonService commonService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerUser(@ReqCheckParam RegisterRequest registerRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.addUserInfo(registerRequest));
    }

    @RequestMapping(value = "/registerDetail", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerUserDetail(UserVo userVo) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.addUserInfoDetail(userVo));
    }

    @RequestMapping(value = "/checkUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkUser(@ReqCheckParam LoginRequest loginRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(commonService.checkUserInfo(loginRequest));
    }

    @RequestMapping(value = "/freshUserInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String freshUserIno(String phone) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.freshUserInfo(phone));
    }
}
