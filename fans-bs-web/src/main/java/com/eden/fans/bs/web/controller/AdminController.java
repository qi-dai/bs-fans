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
 * Created by Administrator on 2016/3/22.
 */
@Controller
@RequestMapping(value = "/manager")
public class AdminController {
    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private IUserService userService;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/setAdmin", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String setAdmin(String adminUserCode,String userCode) throws Exception {
        return gson.toJson(userService.setAdminRole(adminUserCode, userCode));
    }
}
