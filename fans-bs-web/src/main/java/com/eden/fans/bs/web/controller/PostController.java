package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.enu.PostType;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.response.PostErrorCodeEnum;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.eden.fans.bs.service.IPostService;
import com.eden.fans.bs.service.IUserPostService;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private IPostService postService;
    @Autowired
    private IUserPostService userPostService;

    private static Gson gson = GsonUtil.getGson();

    /**
     * 创建帖子
     * @param appCode
     * @param postInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createPost(@RequestParam(value="appCode",required=true) String appCode,PostInfo postInfo) throws Exception {
        // 普通帖子默认都是待审状态
        ServiceResponse<Boolean> response = null;
        if(PostType.ADVERT.getValue() == postInfo.getType().getValue()){
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.CREATE_POST_AUTH_FAILED);
            return gson.toJson(response);
        }

        postInfo.setStatus(PostStatus.CHECK);
        boolean result = postService.createPost(appCode, postInfo);
        if(result){
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.CREATE_POST_SUCCESS);
        } else {
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.CREATE_POST_FAILED);
        }
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 分页获取帖子（所有）
     * @param appCode
     * @param pageNum
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/obtainPostByPage", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String obtainPostByPage(@RequestParam(value="appCode",required=true) String appCode,Integer pageNum) throws Exception {
        String result = postService.obtainPostByPage(appCode, pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 根据帖子的创建人获取帖子
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/obtainPostByUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String obtainPostByUser(@RequestParam(value="appCode",required=true) String appCode,
                                   @RequestParam(value="userCode",required=true) Long userCode,Integer pageNum) throws Exception {
        String result = postService.obtainPostByUserCode(appCode,userCode,pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 根据帖子的id获取帖子的基本信息
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/obtainPostById", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String obtainPostById(@RequestParam(value="appCode",required=true) String appCode,String postId) throws Exception {
        String result = postService.obtainPostById(appCode,postId);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 点赞、取消点赞帖子
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/praisePost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String praisePost(@RequestParam(value="appCode",required=true) String appCode,
                             @RequestParam(value="postId",required=true) String postId,PraiseUser praiseUser) throws Exception {
        logger.error("帖子点赞信息，appCode:{},postId:{},praiseUser:{}",new Object[]{appCode,postId,gson.toJson(praiseUser)});
        ServiceResponse<Boolean> response = null;
        boolean result = postService.updatePraiseUsers(appCode, postId, praiseUser);
        if(result){
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.PRAISE_POST_SUCCESS);
        } else {
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.PRAISE_POST_FAILED);
            logger.error("帖子点赞失败，appCode:{},postId:{},praiseUser:{}",new Object[]{appCode,postId,gson.toJson(praiseUser)});
        }
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 关注、取消关注帖子
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/concernPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String concernPost(@RequestParam(value="appCode",required=true) String appCode,
                              @RequestParam(value="postId",required=true) String postId, ConcernUser concernUser) throws Exception {
        logger.error("关注帖子信息，appCode:{},postId:{},concernUser:{}",new Object[]{appCode,postId,gson.toJson(concernUser)});
        ServiceResponse<Boolean> response = null;
        boolean result = postService.updateConcernUsers(appCode, postId, concernUser);
        if(result){
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.CONCERN_POST_SUCCESS);
        } else {
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.CONCERN_POST_FAILED);
            logger.error("关注帖子失败，appCode:{},postId:{},concernUser:{}",new Object[]{appCode,postId,gson.toJson(concernUser)});
        }
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 回帖
     * @param appCode
     * @param postId
     * @param replyPostInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/replyPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String replyPost(@RequestParam(value="appCode",required=true) String appCode,
                            @RequestParam(value="postId",required=true) String postId, ReplyPostInfo replyPostInfo) throws Exception {
        logger.info("回帖信息，appCode:{},postId:{},replyPostInfo:{}",new Object[]{appCode,postId,gson.toJson(replyPostInfo)});
        boolean result = postService.updateReplyInfos(appCode,postId,replyPostInfo);
        ServiceResponse<Boolean> response = null;
        if(result){
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.REPLY_POST_SUCCESS);
        } else {
            response = new ServiceResponse<Boolean>(PostErrorCodeEnum.REPLY_POST_FAILED);
            logger.error("回帖失败，appCode:{},postId:{},replyPostInfo:{}",new Object[]{appCode,postId,gson.toJson(replyPostInfo)});
        }
        response.setResult(result);
        return gson.toJson(response);
    }
    /**
     * 根获取所有关注的用户
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/allConcernUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String allConcernUser(@RequestParam(value="appCode",required=true) String appCode,
                                 @RequestParam(value="postId",required=true) String postId) throws Exception {
        logger.info("获取所有关注的用户，appCode:{},postId:{}",appCode,postId);
        String result = postService.queryAllConcernUsers(appCode,postId);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 获取所有点赞的用户
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/allPraiseUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String allPraiseUser(@RequestParam(value="appCode",required=true) String appCode,
                                @RequestParam(value="postId",required=true) String postId) throws Exception {
        logger.info("获取所有点赞的用户，appCode:{},postId:{}",appCode,postId);
        String result = postService.queryAllPraiseUsers(appCode,postId);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 获取所有回帖信息列表
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/allReplyPostInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String allReplyPostInfo(@RequestParam(value="appCode",required=true) String appCode,
                                   @RequestParam(value="postId",required=true) String postId) throws Exception {
        logger.info("获取所有回帖信息，appCode:{},postId:{}",appCode,postId);
        String result = postService.queryAllReplyPostInfos(appCode,postId);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/concernUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String concernUser(@RequestParam(value="appCode",required=true) String appCode,
                              @RequestParam(value="postId",required=true) String postId,Integer pageNum) throws Exception {
        logger.info("根据帖子的标志获取帖子下关注的用户，appCode:{},postId:{},pageNum:{}",new Object[]{appCode,postId,pageNum});
        String result = postService.queryConcernUsersByPage(appCode, postId, pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 获根据帖子的标志获取帖子下点赞的用户列表
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/praiseUser", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String praiseUser(@RequestParam(value="appCode",required=true) String appCode,
                             @RequestParam(value="postId",required=true) String postId,Integer pageNum) throws Exception {
        logger.info("获根据帖子的标志获取帖子下点赞的用户，appCode:{},postId:{},pageNum:{}",new Object[]{appCode,postId,pageNum});
        String result = postService.queryPraiseUsersByPage(appCode, postId, pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     * 根据帖子的标志获取回帖信息列表
     * @param appCode
     * @param postId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/replyPostInfo", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String replyPostInfo(@RequestParam(value="appCode",required=true) String appCode,
                                @RequestParam(value="postId",required=true) String postId,Integer pageNum) throws Exception {
        logger.info("根据帖子的标志获取回帖信息，appCode:{},postId:{},pageNum",new Object[]{appCode,postId,pageNum});
        String result = postService.queryReplyPostInfosByPage(appCode, postId, pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     *  用户获取自己发布的帖子（包含未审批的）
     * @param appCode
     * @param userCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String myPost(@RequestParam(value="appCode",required=true) String appCode,
                         @RequestParam(value="userCode",required=true) Long userCode,Integer pageNum) throws Exception {
        String result = postService.myPost(appCode,userCode,pageNum);
        ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.SUCCESS);
        response.setResult(result);
        return gson.toJson(response);
    }

    /**
     *  更新帖子状态：只有发帖人可以更改帖子的状态
     * @param appCode
     * @param postId
     * @postStatus
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatePost(@RequestParam(value="appCode",required=true) String appCode,
                             @RequestParam(value="postId",required=true) String postId,
                             @RequestParam(value="userCode",required=true) Long userCode,PostStatus postStatus) throws Exception {
        DBObject dbObject = postService.obtainMyPostById(appCode, postId);
        if(null == dbObject){
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_FAILED);
            response.setResult(false+"");
            return gson.toJson(response);
        }
        if(userCode == (Long)dbObject.get("userCode")){
            boolean result = postService.updateStatus(appCode,postId,postStatus,null);
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_SUCCESS);
            response.setResult(result+"");
            return gson.toJson(response);
        } else {
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_AUTH_FAILED);
            response.setResult(false+"");
            return gson.toJson(response);
        }
    }
}
