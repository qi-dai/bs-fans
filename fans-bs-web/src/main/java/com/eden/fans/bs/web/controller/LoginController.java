package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.request.LoginRequest;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.ICommonService;
import com.eden.fans.bs.service.IUserService;
import com.eden.fans.bs.web.aop.BaseEntity;
import com.google.gson.Gson;
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
    /*static {
        BaseEntity.init(LoginRequest.class);
    }*/
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ICommonService commonService;
    @Autowired
    private IUserService userService;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerUser(String phone, String password,String platform) throws Exception {
        return gson.toJson(userService.addUserInfo(phone, password, platform));
    }

    @RequestMapping(value = "/registerDetail", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String registerUserDetail(UserVo userVo) throws Exception {
        return gson.toJson(userService.addUserInfoDetail(userVo));
    }

    @RequestMapping(value = "/checkUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkUser(@ReqCheckParam LoginRequest loginRequest) throws Exception {
        return gson.toJson(commonService.checkUserInfo(loginRequest));
    }
}
