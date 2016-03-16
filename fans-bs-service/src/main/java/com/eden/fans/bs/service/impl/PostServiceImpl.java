package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.ICardDao;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.eden.fans.bs.service.ICardService;
import com.eden.fans.bs.service.IPostService;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class PostServiceImpl implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private static Gson PARSER = new Gson();

    @Autowired
    private IPostDao postDao;

    /**
     * 创建帖子
     *
     * @param appCode
     * @param postInfo
     */
    @Override
    public boolean createPost(String appCode, PostInfo postInfo) {
        // 创建Mongo存储对象并进行属性赋值
        DBObject dbObject = new BasicDBObject();
        post2DBObject(postInfo,dbObject);
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
        return false;
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
        return false;
    }

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countPraiseUsers(String appCode) {
        return null;
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String appCode) {
        return null;
    }

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param pageNum
     */
    @Override
    public List<ConcernUser> queryConcernUsersByPage(String appCode, String id, Integer pageNum) {
        return null;
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param pageNum
     */
    @Override
    public List<PraiseUser> queryPraiseUsersByPage(String appCode, String id, Integer pageNum) {
        return null;
    }

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param pageNum
     */
    @Override
    public List<ReplyPostInfo> queryReplyPostInfosByPage(String appCode, String id, Integer pageNum) {
        return null;
    }

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countReplyPostInfos(String appCode) {
        return null;
    }

    private void post2DBObject(PostInfo postInfo,DBObject dbObject){
        dbObject.put("title", postInfo.getTitle());
        dbObject.put("type", postInfo.getType());
        dbObject.put("content", postInfo.getContent());
        dbObject.put("userCode", postInfo.getUserCode());
        dbObject.put("imgs", null == postInfo.getImgs() ? "" : PARSER.toJson(postInfo.getImgs()));
        dbObject.put("videos", null == postInfo.getVideos() ? "" : PARSER.toJson(postInfo.getVideos()));
        dbObject.put("musics", null == postInfo.getMusics() ? "" : PARSER.toJson(postInfo.getMusics()));
        dbObject.put("others", null == postInfo.getOthers() ? "" : PARSER.toJson(postInfo.getOthers()));
        dbObject.put("createDate", postInfo.getCreateDate());
        dbObject.put("publishDate", postInfo.getPublishDate());
        dbObject.put("status", postInfo.getStatus());
        dbObject.put("level",postInfo.getLevel());
        dbObject.put("operatorList", null == postInfo.getOperatorList()?"[]":PARSER.toJson(postInfo.getOperatorList()));
        dbObject.put("concernUsers","[]");
        dbObject.put("praiseUsers","[]");
        dbObject.put("replyPostInfos","[]");
    }
}
