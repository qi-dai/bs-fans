package com.eden.fans.bs.web.controller;

/**
 * Created by Administrator on 2016/4/29.
 */

import com.eden.fans.bs.common.util.UserGsonUtil;
import com.eden.fans.bs.dao.IDictDao;
import com.eden.fans.bs.domain.request.DictRequest;
import com.eden.fans.bs.service.IDictService;
import com.eden.fans.bs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dict")
public class DictController{

    @Autowired
    private IDictService dictService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String qryDict(DictRequest dictRequest) throws Exception {
        return UserGsonUtil.getGson().toJson(dictService.queryDictVos(dictRequest));
    }
}
