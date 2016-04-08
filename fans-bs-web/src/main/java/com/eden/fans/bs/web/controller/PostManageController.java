package com.eden.fans.bs.web.controller;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.response.PostErrorCodeEnum;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.IPostService;
import com.eden.fans.bs.service.IUserPostService;
import com.eden.fans.bs.service.IUserService;
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
@RequestMapping(value = "/mpost")
public class PostManageController {
    private static Logger logger = LoggerFactory.getLogger(PostManageController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IUserPostService userPostService;

    private static Gson gson = GsonUtil.getGson();

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
     *  更新帖子状态：只有管理员和发帖人可以更改帖子的状态（包括审批帖子，删除操作）
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
                             @RequestParam(value="userCode",required=true) Long userCode,
                             Long postChecker,PostStatus postStatus) throws Exception {
        // 校验操作用户身份（管理员或者发帖人）
        if(!isAdmin(userCode)){
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_FAILED);
            return gson.toJson(response);
        }
        DBObject dbObject = postService.obtainMyPostById(appCode, postId);
        if(null == dbObject){
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_FAILED);
            return gson.toJson(response);
        }
        if(userCode == (Long)dbObject.get("userCode")){
            boolean result = postService.updateStatus(appCode,postId,postStatus,userCode,postChecker);
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_SUCCESS);
            response.setResult(result+"");
            return gson.toJson(response);
        } else {
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.UPDATE_POST_FAILED);
            return gson.toJson(response);
        }

    }


    /**
     * 获取待审批的帖子
     * @param appCode
     * @param userCode
     * @postStatus
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/approvalPost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approvalPost(@RequestParam(value="appCode",required=true) String appCode,
                               @RequestParam(value="userCode",required=true) Long userCode,Integer pageNum) throws Exception {
        // 校验用户身份 只有管理员才能审批帖子
        if(isAdmin(userCode)){
            // 获取待审批的帖子
            postService.queryApprovalPost(appCode, pageNum);
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.GET_APPROVAL_POST_SUCCESS);
            return gson.toJson(response);
        } else {
            ServiceResponse<String> response = new ServiceResponse<String>(PostErrorCodeEnum.GET_APPROVAL_POST_FAILED);
            return gson.toJson(response);
        }
    }

    /**
     * 校验用户是否是管理员
     * @param userCode
     * @return
     */
    private boolean isAdmin(Long userCode){
        UserVo userVo = userService.qryUserInfo(userCode).getResult().getUserVo();
        if(null == userVo){
            return false;
        } else {
            if(Constant.ADMIN_ROLE_CODE.equals(userVo.getUserRole())){
                return true;
            } else {
                return false;
            }
        }
    }

}
