package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.request.MediaRequest;
import com.eden.fans.bs.domain.request.QryUserMediaVos;
import com.eden.fans.bs.domain.request.UpdateMediaRequest;
import com.eden.fans.bs.service.IUserMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/4/24.
 */
@Controller
@RequestMapping(value = "/userMedia")
public class UserMediaController {
    private static Logger logger = LoggerFactory.getLogger(UserMediaController.class);

    @Autowired
    private IUserMediaService userMediaService;

    @RequestMapping(value = "/saveMedia", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveMedia(@ReqCheckParam MediaRequest saveMediaRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userMediaService.addMediaVo(saveMediaRequest));
    }


    @RequestMapping(value = "/getUserMedias", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getUserMedias(@ReqCheckParam QryUserMediaVos qryUserMediaVos) throws Exception {
        return UserGsonUtil.getGson().toJson(userMediaService.getUserMediaVos(qryUserMediaVos));
    }

    @RequestMapping(value = "/updateUserMedia", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateUserMedia(@ReqCheckParam UpdateMediaRequest updateMediaRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(userMediaService.updateUserMediaVo(updateMediaRequest));
    }

}
