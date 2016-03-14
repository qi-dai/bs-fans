package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.domain.Fans;
import com.eden.fans.bs.service.IFansService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Controller
@RequestMapping("/fans")
public class FansController {
    private static Logger logger = LoggerFactory.getLogger(FansController.class);

    private static Gson gson = new Gson();

    @Autowired
    private IFansService fansService;

    @ResponseBody
    @RequestMapping("/testFans")
    public void testFans() {
        fansService.test();
    }

    @ResponseBody
    @RequestMapping("/addFans")
    public String addFans(@RequestBody(required=true)Fans fans,@RequestBody(required=true) Integer code,HttpServletRequest request,HttpServletResponse response){
        fansService.addFans(fans,code);
        return gson.toJson(fans).toString();
    }
}
