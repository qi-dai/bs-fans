package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.service.IPostService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 帖子的入口操作类
 * 1、所有对帖子点赞，关注和评论等对贴子的操作都会先去判断缓存的数值，存在即更新，否则调用相关联的count接口，并更新缓存
 * 2、所有对帖子有关的分页查询的操作都要先去缓存中读取相关的数值，然后调分页查询接口；如果缓存数值不存在，调相关联的count接口，
 *    然后获取缓存数值，并更新缓存，然后掉相关的分页接口。
 *
 * Created by shengyanpeng on 2016/3/25.
 */
@Controller
@RequestMapping(value = "/post")
public class PostController {

    private static Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private IPostService PostService;

    private static Gson gson = GsonUtil.getGson();

    @RequestMapping(value = "/createPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createPost(PostInfo postInfo) throws Exception {
        return null;
    }

    @RequestMapping(value = "/obtainPostByPage", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String obtainPostByPage(String appCode,Integer pageNum) throws Exception {
       return null;
    }

}
