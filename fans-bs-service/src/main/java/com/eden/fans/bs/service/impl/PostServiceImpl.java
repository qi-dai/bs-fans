package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.dao.IUserPostDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.enu.PostBoutique;
import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostOnTop;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.ICommonService;
import com.eden.fans.bs.service.IPostService;
import com.eden.fans.bs.service.concurrent.ObtainReplyAndPraiseCountCallable;
import com.eden.fans.bs.service.concurrent.ObtainReplyAndPraiseCountCallableUtil;
import com.eden.fans.bs.service.concurrent.ObtainUserInfoCallable;
import com.eden.fans.bs.service.concurrent.ObtainUserInfoCallableUtil;
import com.google.gson.internal.LinkedHashTreeMap;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class PostServiceImpl implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserPostDao userPostDao;
    @Autowired
    private IPostDao postDao;
    @Autowired
    private RedisCache redisCache;
    /**
     * 分页获取帖子列表
     *
     * @param appCode
     * @param pageNum
     * @return
     */
    @Override
    public String obtainPostByPage(String appCode,Integer postType,Integer pageNum) {
        StringBuilder stringBuilder = new StringBuilder();
        Long postCount = postDao.countPost(appCode,postType);

        if(null == pageNum || pageNum < 0)
            pageNum = 0;

        List<DBObject> dbObjectList = postDao.obtainPostByPage(appCode,postType,pageNum);
        // 批量获取用户信息,点赞数和回帖数
        Map<String,Map<String,String>> postHeadMap = this.obtainPostHead(dbObjectList,appCode);

        postHead2String(stringBuilder,dbObjectList,postCount,false,postHeadMap);
        logger.info("分页获取帖子列表:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 根据用户标识获取用户发的帖子列表
     *
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     */
    @Override
    public String obtainPostByUserCode(String appCode, Long userCode, Integer pageNum) {
        StringBuilder stringBuilder = new StringBuilder();
        Long postCount = postDao.countPostByUserCode(appCode, userCode);

        if(null == pageNum || pageNum < 0)
            pageNum = 0;

        List<DBObject> dbObjectList = postDao.obtainPostByUserCode(appCode, userCode, pageNum);
        // 批量获取用户信息,点赞数和回帖数
        Map<String,Map<String,String>> postHeadMap = this.obtainPostHead(dbObjectList,appCode);
        postHead2String(stringBuilder,dbObjectList,postCount,true,postHeadMap);
        logger.info("根据用户分页获取帖子列表:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 获取我的帖子
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     */
    @Override
    public String myPost(String appCode, Long userCode, Integer pageNum) {
        StringBuilder stringBuilder = new StringBuilder();
        Long postCount = postDao.countMyPost(appCode, userCode);

        if(null == pageNum || pageNum < 0)
            pageNum = 0;
        List<DBObject> dbObjectList = postDao.myPost(appCode, userCode, pageNum);
        // 批量获取用户信息,点赞数和回帖数
        Map<String,Map<String,String>> postHeadMap = this.obtainPostHead(dbObjectList,appCode);
        postHead2String(stringBuilder,dbObjectList,postCount,false,postHeadMap);
        logger.info("根据用户分页获取帖子列表:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }


    @Override
    public String obtainPostById(String appCode, String postId) {
        DBObject object = postDao.obtainPostById(appCode, postId);
        if(null != object){
            StringBuilder stringBuilder = new StringBuilder();
            postDbObject2String(object,stringBuilder);
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    @Override
    public DBObject obtainMyPostById(String appCode, String postId) {
        DBObject object = postDao.obtainPostById(appCode, postId);
        return object;
    }


    /**
     * 创建帖子
     *
     * @param appCode
     * @param postInfo
     */
    @Override
    public boolean createPost(String appCode, PostInfo postInfo) {
        Map<String,Object> map = new LinkedHashTreeMap<String, Object>();
        post2Map(postInfo,map);
        DBObject dbObject = new BasicDBObject();
        dbObject.putAll(map);
        return postDao.createPost(appCode, dbObject);
    }

    /**
     * 更新帖子状态（status）
     *
     * @param appCode
     * @param postId
     * @param status
     */
    @Override
    public boolean updateStatus(String appCode, String postId,PostStatus status,Long postChecker) {
        return  postDao.updateStatus(appCode, postId, status,postChecker);
    }

    /**
     * 更新帖子状态（onTop）
     *
     * @param appCode
     * @param postId
     * @param postOnTop
     * @param postChecker
     */
    @Override
    public boolean updateOnTop(String appCode, String postId, PostOnTop postOnTop, Long postChecker) {
        return  postDao.updateOnTop(appCode, postId, postOnTop,postChecker);
    }

    /**
     * 更新帖子状态（boutique）
     *
     * @param appCode
     * @param postId
     * @param postBoutique
     * @param postChecker
     */
    @Override
    public boolean updateBoutique(String appCode, String postId, PostBoutique postBoutique, Long postChecker) {
        return  postDao.updateBoutique(appCode, postId, postBoutique, postChecker);
    }

    /**
     * 更新帖子的点赞用户列表（点赞、取消点赞）,更新成功之后同时更新“我的点赞”列表
     *
     * @param appCode
     * @param postId
     * @param praiseUser
     */
    @Override
    public boolean updatePraiseUsers(String appCode, String postId, PraiseUser praiseUser) {
        boolean result_= true;
        boolean result = postDao.updatePraiseUsers(appCode, postId, praiseUser);
        if(result){
            DBObject object = postDao.obtainPostById(appCode,postId);
            Map<String,Object> praisePostMap = new LinkedHashMap<String, Object>();
            praisePostMap.put("postId",postId);
            praisePostMap.put("title",object.get("title"));
            praisePostMap.put("status",praiseUser.getPraise().getValue());
            praisePostMap.put("time",new Date());
            result_ = userPostDao.praisePost(appCode, praiseUser.getUserCode(), praiseUser.getUserName(), praisePostMap);
        }

        return  result&result_;
    }

    /**
     * 更新帖子的关注用户列表（关注、取消关注，更新成功之后同时更新“我的关注”列表
     *
     * @param appCode
     * @param postId
     * @param concernUser
     */
    @Override
    public boolean updateConcernUsers(String appCode, String postId, ConcernUser concernUser) {
        boolean result_ = true;
        boolean result = postDao.updateConcernUsers(appCode, postId, concernUser);

        if(result){
            DBObject object = postDao.obtainPostById(appCode,postId);
            Map<String,Object> concernPostMap = new LinkedHashMap<String, Object>();
            concernPostMap.put("postId",postId);
            concernPostMap.put("title",object.get("title"));
            concernPostMap.put("status",concernUser.getConcern().getValue());
            concernPostMap.put("time",new Date());
            userPostDao.concernPost(appCode,concernUser.getUserCode(),concernUser.getUserName(),concernPostMap);
        }

        return  result&result_;
    }

    /**
     * 更新回帖
     *
     * @param appCode
     * @param postId
     * @param replyPostInfo
     * @return
     */
    @Override
    public boolean updateReplyInfos(String appCode, String postId, ReplyPostInfo replyPostInfo) {
        replyPostInfo.setReplyTime(new Date());
        return postDao.updateReplyInfos(appCode, postId, replyPostInfo);
    }

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     * @param postId
     * @param appCode
     */
    @Override
    public Long countPraiseUsers(String postId,String appCode) {
        return postDao.countPraiseUsers(postId, appCode);
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     * @param postId
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String postId,String appCode) {
        return postDao.countConcernUsers(postId, appCode);
    }

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     * @param postId
     * @param appCode
     */
    @Override
    public Long countReplyPostInfos(String postId,String appCode) {
        return postDao.countReplyPostInfos(postId, appCode);
    }

    /**
     * 获取所有关注的用户
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllConcernUsers(String appCode, String postId) {
        return postDao.queryAllConcernUsers(appCode, postId);
    }

    /**
     * 获取所有点赞的用户
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllPraiseUsers(String appCode, String postId) {
        return  postDao.queryAllPraiseUsers(appCode, postId);
    }

    /**
     * 获取所有回帖信息列表
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllReplyPostInfos(String appCode, String postId) {
        return postDao.queryAllReplyPostInfos(appCode, postId);
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryConcernUsersByPage(String appCode, String postId, Integer pageNum) {
        Long postCount = postDao.countConcernUsers(appCode, postId);
        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryConcernUsersByPage(appCode, postId, pageNum,postCount);
    }

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryPraiseUsersByPage(String appCode, String postId, Integer pageNum) {
        Long postCount = postDao.countPraiseUsers(appCode, postId);

        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryPraiseUsersByPage(appCode, postId, pageNum,postCount);
    }

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryReplyPostInfosByPage(String appCode, String postId, Integer pageNum) {
        Long postCount = postDao.countReplyPostInfos(appCode,postId);
        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryReplyPostInfosByPage(appCode, postId, pageNum,postCount);
    }

    /**
     * 获取待审批的帖子
     *
     * @param appCode
     * @param pageNum
     * @return
     */
    @Override
    public String queryApprovalPost(String appCode, Integer pageNum) {
        Long postCount = postDao.countApprovalPost(appCode);
        if(null == pageNum || pageNum<0||pageNum>(postCount/10+1))
            pageNum = 0;
        return postDao.queryApprovalPost(appCode,pageNum,postCount);
    }

    /**
     * 根据用户标识获取已审批的帖子列表
     *
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     */
    @Override
    public String queryApprovedPost(String appCode, Long userCode, Integer pageNum) {
        StringBuilder stringBuilder = new StringBuilder();
        Long postCount = postDao.countApprovedPost(appCode, userCode);

        if(null == pageNum || pageNum < 0)
            pageNum = 0;
        List<DBObject> dbObjectList = postDao.queryApprovedPost(appCode, userCode, pageNum);
        Map<String,Map<String,String>> postHeadMap = this.obtainPostHead(dbObjectList,appCode);
        postHead2String(stringBuilder,dbObjectList,postCount,false,postHeadMap);
        return stringBuilder.toString();
    }

    /**
     * 将帖子转成map（减少序列号和反序列化的操作）
     * @param postInfo
     * @param map
     */
    private void post2Map(PostInfo postInfo,Map<String,Object> map){
        map.put("title",postInfo.getTitle());
        map.put("type",postInfo.getType().getValue());
        map.put("content",postInfo.getContent());
        map.put("userCode",postInfo.getUserCode());
        map.put("imgs",postInfo.getImgs());
        map.put("videos",postInfo.getVideos());
        map.put("musics",postInfo.getMusics());
        map.put("others",postInfo.getOthers());
        map.put("createDate",new Date());
        map.put("publishDate",new Date());
        map.put("status",postInfo.getStatus().getValue());
        map.put("level",postInfo.getLevel().getValue());
        map.put("operatorList",postInfo.getOperatorList());
        map.put("concernUsers",postInfo.getConcernUsers());
        map.put("praiseUsers",postInfo.getPraiseUsers());
        // 优化点赞数，关注数和回帖数
        map.put("praiseCount",0);
        map.put("concernCount",0);
        map.put("replyCount",0);
        // 置顶和加精标识
        map.put("onTop",0);
        map.put("boutique",0);
    }

    /**
     * 将帖子基本列表显示字段信息转成String
     * @param stringBuilder
     * @param dbObjectList
     * @param postCount
     */
    private void postHead2String(StringBuilder stringBuilder,List<DBObject> dbObjectList,Long postCount,boolean myPost,Map<String,Map<String,String>> postHeadMap){
        if(null != dbObjectList && dbObjectList.size()>0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append("{\"data\":[");
            for(DBObject dbObject:dbObjectList){
                String postId = dbObject.get("_id") + "";
                Map<String,String> map = postHeadMap.get(postId);
                stringBuilder.append("{");
                stringBuilder.append("\"postId\":\"" + postId + "\",");
                stringBuilder.append("\"userCode\":\"" + dbObject.get("userCode") + "\",");
                stringBuilder.append("\"title\":\"" + dbObject.get("title") + "\",");
                stringBuilder.append("\"imgs\":" + ((null == dbObject.get("imgs"))?"[]":dbObject.get("imgs")) + ",");
                stringBuilder.append("\"videos\":" + ((null == dbObject.get("videos"))?"[]":dbObject.get("videos")) +  ",");
                stringBuilder.append("\"musics\":" + ((null == dbObject.get("musics"))?"[]":dbObject.get("musics")) +  ",");
                stringBuilder.append("\"others\":" + ((null == dbObject.get("others"))?"[]":dbObject.get("others")) +  ",");
                stringBuilder.append("\"headImgUrl\":\"" + map.get("headImgUrl") + "\",");
                stringBuilder.append("\"userName\":\"" + map.get("userName") + "\",");
                stringBuilder.append("\"replyCount\":" + map.get("replyCount") + ",");
                stringBuilder.append("\"praiseCount\":" + map.get("praiseCount") + ",");
                if(myPost){
                    stringBuilder.append("\"status\":\"" + PostStatus.getName((Integer)dbObject.get("status")) + "\",");
                }
                stringBuilder.append("\"createDate\":\"" + format.format((Date)dbObject.get("createDate")) + "\"");
                stringBuilder.append("},");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("],\"total\":" + postCount + "}");
        }
    }

    /**
     * 将帖子的基本信息对象转成String
     * @param dbObject
     * @param stringBuilder
     */
    private void postDbObject2String(DBObject dbObject,StringBuilder stringBuilder){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringBuilder.append("{");
        stringBuilder.append("\"postId\":\"" + dbObject.get("_id") + "\",");
        stringBuilder.append("\"title\":\"" + dbObject.get("title") + "\",");
        stringBuilder.append("\"content\":\"" + dbObject.get("content") + "\",");
        stringBuilder.append("\"userCode\":\"" + dbObject.get("userCode") + "\",");
        stringBuilder.append("\"userName\":\"" + dbObject.get("userName") + "\",");

        stringBuilder.append("\"imgs\":" + ((null == dbObject.get("imgs"))?"[]":dbObject.get("imgs")) + ",");
        stringBuilder.append("\"videos\":" + ((null == dbObject.get("videos"))?"[]":dbObject.get("videos")) + ",");
        stringBuilder.append("\"musics\":" + ((null == dbObject.get("musics"))?"[]":dbObject.get("musics")) + ",");
        stringBuilder.append("\"others\":" + ((null == dbObject.get("others"))?"[]":dbObject.get("others")) + ",");

        stringBuilder.append("\"createDate\":\"" + format.format((Date)dbObject.get("createDate")) + "\",");
        stringBuilder.append("\"publishDate\":\"" + format.format((Date)dbObject.get("publishDate")) + "\",");
        stringBuilder.append("\"status\":\"" + PostStatus.getName((Integer)dbObject.get("status")) + "\",");
        stringBuilder.append("\"level\":\"" + PostLevel.getName((Integer)dbObject.get("level")) + "\"");
        stringBuilder.append("}");
    }

    /**
     * 获取帖子头相关信息（用户名称，用户头像,回帖数、点赞数等信息）
     * @param dbObjectList
     * @return
     */
    private Map<String,Map<String,String>> obtainPostHead(List<DBObject> dbObjectList,String appCode){
        Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String, String>>(10);
        List<ObtainReplyAndPraiseCountCallable> countCallableList = new ArrayList<ObtainReplyAndPraiseCountCallable>(10);
        List<ObtainUserInfoCallable> userCallableList = new ArrayList<ObtainUserInfoCallable>(10);
        for(DBObject dbObject:dbObjectList){
            ObtainReplyAndPraiseCountCallable countCallable = new ObtainReplyAndPraiseCountCallable();
            countCallable.setAppCode(appCode);
            countCallable.setPostDao(postDao);
            countCallable.setPostId(dbObject.get("_id")+"");
            countCallableList.add(countCallable);

            ObtainUserInfoCallable userInfoCallable = new ObtainUserInfoCallable();
            userInfoCallable.setUserCode(Long.valueOf(dbObject.get("userCode")+""));
            userInfoCallable.setUserDao(userDao);
            userInfoCallable.setPostId(dbObject.get("_id")+"");
            userCallableList.add(userInfoCallable);
        }
        List<Future<Map<String,Map<String,String>>>> futureList = ObtainReplyAndPraiseCountCallableUtil.ObtainReplyAndPraiseCount(countCallableList);
        Map<String,Map<String,String>> userMap = ObtainUserInfoCallableUtil.ObtainReplyAndPraiseCount(userCallableList);
        for(Future<Map<String,Map<String,String>>> future:futureList){
            try {
                try {
                    Map<String,Map<String,String>> postMap = future.get();
                    String postId = postMap.keySet().iterator().next();

                    Map<String,String> countMap = postMap.get(postId);
                    Map<String,String> userTmpMap = userMap.get(postId);
                    userTmpMap.putAll(countMap);

                    resultMap.put(postId,userTmpMap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (ExecutionException e) {
                // TODO
            }
        }
        return resultMap;
    }
}
