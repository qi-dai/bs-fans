package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.domain.response.BaseCodeEnum;
import com.eden.fans.bs.domain.response.PostErrorCodeEnum;
import com.eden.fans.bs.service.IUserPostService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户维度操作帖子的入口
 * 1、所有对帖子点赞，关注和评论等对贴子的操作都会先去判断缓存的数值，存在即更新，否则调用相关联的count接口，并更新缓存
 * 2、所有对帖子有关的分页查询的操作都要先去缓存中读取相关的数值，然后调分页查询接口；如果缓存数值不存在，调相关联的count接口，
 *    然后获取缓存数值，并更新缓存，然后掉相关的分页接口。
 *
 * Created by shengyanpeng on 2016/3/25.
 */
@Controller
@RequestMapping(value = "/userPost")
public class UserPostController {

    private static Logger logger = LoggerFactory.getLogger(UserPostController.class);
    @Autowired
    private IUserPostService userPostService;

    private static Gson gson = GsonUtil.getGson();

    @RequestMapping(value = "/queryPraisePost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryPraisePost(@RequestParam(value="appCode",required=true) String appCode,
                                  @RequestParam(value="userCode",required=true) Long userCode,Integer pageNum) throws Exception {
        StringBuilder response = new StringBuilder();
        String result = userPostService.queryPraisePostByPage(appCode, userCode, pageNum);
        createResponseHeader(response,PostErrorCodeEnum.SUCCESS);
        response.append("\"result\":" + result +"}");
        return response.toString();
    }

    @RequestMapping(value = "/queryConcernPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryConcernPost(@RequestParam(value="appCode",required=true) String appCode,
                   @RequestParam(value="userCode",required=true) Long userCode,Integer pageNum) throws Exception {
        StringBuilder response = new StringBuilder();
        String result = userPostService.queryConcernPostByPage(appCode, userCode, pageNum);
        createResponseHeader(response,PostErrorCodeEnum.SUCCESS);
        response.append("\"result\":" + result +"}");
        return response.toString();
    }

    @RequestMapping(value = "/queryAllPraisePost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryAllPraisePost(@RequestParam(value="appCode",required=true) String appCode,
                                     @RequestParam(value="userCode",required=true) Long userCode) throws Exception {
        StringBuilder response = new StringBuilder();
        String result = userPostService.queryAllPraisePost(appCode, userCode);
        createResponseHeader(response,PostErrorCodeEnum.SUCCESS);
        response.append("\"result\":" + result +"}");
        return response.toString();
    }

    @RequestMapping(value = "/queryAllConcernPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryAllConcernPost(@RequestParam(value="appCode",required=true) String appCode,
                                      @RequestParam(value="userCode",required=true) Long userCode) throws Exception {
        StringBuilder response = new StringBuilder();
        String result = userPostService.queryAllConcernPost(appCode, userCode);
        createResponseHeader(response,PostErrorCodeEnum.SUCCESS);
        response.append("\"result\":" + result +"}");
        return response.toString();
    }

    private void createResponseHeader(StringBuilder response, BaseCodeEnum baseCodeEnum){
        response.append("{\"code\":" + baseCodeEnum.getCode()+",");
        response.append("\"msg\":\"" + baseCodeEnum.getMsg()+"\",");
        response.append("\"detail\":\"" + baseCodeEnum.getDetail()+"\",");
    }
}
