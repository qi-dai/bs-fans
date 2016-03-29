package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.domain.request.SetAdminRequest;
import com.eden.fans.bs.domain.request.StatusUpdateRequest;
import com.eden.fans.bs.service.IUserService;
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
@RequestMapping(value = "/bsmanager")
public class ManagerUserController {
    private static Logger logger = LoggerFactory.getLogger(ManagerUserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/setAdmin", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String setAdmin(SetAdminRequest setAdminRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.setAdminRole(setAdminRequest));
    }

    @RequestMapping(value = "/updateUserStatus", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUserStatus(StatusUpdateRequest statusUpdateRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userService.updateUserStatus(statusUpdateRequest));
    }
}
