package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.IUserService;
import com.google.gson.Gson;
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

    @RequestMapping(value = "/qryUserInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String qryUserInfo(UserVo userVo) throws Exception {
        return gson.toJson(userService.qryUserInfo(userVo.getUserCode()));
    }
}
