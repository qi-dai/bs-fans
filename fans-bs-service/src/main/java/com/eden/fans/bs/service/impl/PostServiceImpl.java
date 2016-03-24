package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.GsonEnumUtil;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.enu.PostType;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.eden.fans.bs.service.IPostService;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class PostServiceImpl implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private static Gson PARSER = GsonEnumUtil.enumParseGson();

    @Autowired
    private IPostDao postDao;

    @Override
    public PostInfo obtainPostById(String appCode, String id) {
        PostInfo postInfo = null;
        DBObject object = postDao.obtainPostById(appCode,id);
        if(null != object){
            postInfo = PARSER.fromJson(JSON.serialize(object),PostInfo.class);
        }
        return postInfo;
    }

    /**
     * 创建帖子
     *
     * @param appCode
     * @param postInfo
     */
    @Override
    public boolean createPost(String appCode, PostInfo postInfo) {
        String postString = PARSER.toJson(postInfo);
        DBObject dbObject = (DBObject)JSON.parse(postString);
        return postDao.createPost(appCode,dbObject);
    }

    /**
     * 更新帖子状态（status）
     *
     * @param appCode
     * @param id
     * @param status
     */
    @Override
    public boolean updateStatus(String appCode, String id, Integer status) {
        return postDao.updateStatus(appCode,id,status);
    }

    /**
     * 更新帖子的点赞用户列表（点赞、取消点赞）
     *
     * @param appCode
     * @param id
     * @param praiseUser
     */
    @Override
    public boolean updatePraiseUsers(String appCode, String id, PraiseUser praiseUser) {
        return  postDao.updatePraiseUsers(appCode,id,praiseUser);
    }

    /**
     * 更新帖子的关注用户列表（关注、取消关注）
     *
     * @param appCode
     * @param id
     * @param concernUser
     */
    @Override
    public boolean updateConcernUsers(String appCode, String id, ConcernUser concernUser) {
        return postDao.updateConcernUsers(appCode,id,concernUser);
    }

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countPraiseUsers(String postId,String appCode) {
        return postDao.countPraiseUsers(postId, appCode);
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String postId,String appCode) {
        return postDao.countConcernUsers(postId, appCode);
    }

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param time
     */
    @Override
    public List<ConcernUser> queryConcernUsersByPage(String appCode, String id, Date time) {
        return postDao.queryConcernUsersByPage(appCode, id, time);
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param time
     */
    @Override
    public List<PraiseUser> queryPraiseUsersByPage(String appCode, String id, Date time) {
        return postDao.queryPraiseUsersByPage(appCode, id, time);
    }

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param replyTime
     */
    @Override
    public List<ReplyPostInfo> queryReplyPostInfosByPage(String appCode, String id, Date replyTime) {
        return postDao.queryReplyPostInfosByPage(appCode, id, replyTime);
    }

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countReplyPostInfos(String postId,String appCode) {


        return null;
    }

}
