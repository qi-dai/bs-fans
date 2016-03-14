package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.domain.Fans;
import com.eden.fans.bs.service.ICardService;
import com.eden.fans.bs.service.IFansService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Controller
@RequestMapping("/card")
public class CardController {
    private static Logger logger = LoggerFactory.getLogger(CardController.class);

    private static Gson gson = new Gson();

    @Autowired
    private ICardService cardService;

    @ResponseBody
    @RequestMapping("/testMongo")
    public void testMongo(){
        cardService.test();
    }
}
