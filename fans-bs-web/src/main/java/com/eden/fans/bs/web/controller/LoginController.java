package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.service.ICommonService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lee on 2016/3/15.
 */
@Controller
@RequestMapping(value = "/verifyCode")
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ICommonService commonService;

    private static Gson gson = new Gson();

    @RequestMapping(value = "/validateCode", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    public String validateCode(String phone, String password) throws Exception {
        return gson.toJson(commonService.checkUserInfo(phone, password));
    }
}
