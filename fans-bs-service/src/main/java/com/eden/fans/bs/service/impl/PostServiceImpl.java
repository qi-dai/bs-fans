package com.eden.fans.bs.service.impl;

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

    private static Gson PARSER = new Gson();

    @Autowired
    private IPostDao postDao;

    @Override
    public PostInfo obtainPostById(String appCode, String id) {
        PostInfo postInfo = null;
        BasicDBObject keys = new BasicDBObject();
        setPostKeys(keys);
        DBObject object = postDao.obtainPostById(appCode,id,keys);
        if(null != object){
            postInfo = new PostInfo();
            DBobject2PostInfo(object,postInfo);
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

    /**
     * 创建帖子时 帖子对象转换成mongo对象
     * @param postInfo
     * @param dbObject
     */
    private void post2DBObject(PostInfo postInfo,DBObject dbObject){
        dbObject.put("title", postInfo.getTitle());
        dbObject.put("type", postInfo.getType().getValue());// 此处有可能会根据帖子的类型进行聚合 所以此处保存value
        dbObject.put("content", postInfo.getContent());
        dbObject.put("userCode", postInfo.getUserCode());
        dbObject.put("imgs", null == postInfo.getImgs() ? "" : PARSER.toJson(postInfo.getImgs()));
        dbObject.put("videos", null == postInfo.getVideos() ? "" : PARSER.toJson(postInfo.getVideos()));
        dbObject.put("musics", null == postInfo.getMusics() ? "" : PARSER.toJson(postInfo.getMusics()));
        dbObject.put("others", null == postInfo.getOthers() ? "" : PARSER.toJson(postInfo.getOthers()));
        dbObject.put("createDate", postInfo.getCreateDate());
        dbObject.put("publishDate", postInfo.getPublishDate());
        dbObject.put("status", postInfo.getStatus().getName());
        dbObject.put("level",postInfo.getLevel().getName());
        dbObject.put("operatorList", null == postInfo.getOperatorList()?"[]":PARSER.toJson(postInfo.getOperatorList()));
        // 以下属性是以帖子的维度去获取，帖子基本信息不包含如下属性，同事根据帖子的ID在获取帖子的信息的时候也不做返回的数据
        dbObject.put("concernUsers","[]");
        dbObject.put("praiseUsers","[]");
        dbObject.put("replyPostInfos","[]");
    }

    /**
     * 设置需要获取的帖子的属性
     * @param keys
     */
    private void setPostKeys(BasicDBObject keys){
        keys.put("title",1);
        keys.put("type",1);
        keys.put("content",1);
        keys.put("userCode",1);
        keys.put("imgs",1);
        keys.put("videos",1);
        keys.put("musics",1);
        keys.put("others",1);
        keys.put("createDate",1);
        keys.put("publishDate",1);
        keys.put("status",1);
        keys.put("level",1);
    }

    /**
     * Mongo对象转换成帖子
     * @param dbObject
     * @param postInfo
     */
    private void DBobject2PostInfo(DBObject dbObject,PostInfo postInfo){
        // TODO 如果直接调用 toString 方法如何
        postInfo.setTitle((String)dbObject.get("title"));
        postInfo.setType(PostType.getPostType((String) dbObject.get("type")));
        postInfo.setContent((String) dbObject.get("content"));
        postInfo.setUserCode((Integer) dbObject.get("userCode"));
        postInfo.setImgs("[]".equals(dbObject.get("imgs"))?null:PARSER.fromJson((String)dbObject.get("imgs"),List.class));
        postInfo.setVideos("[]".equals(dbObject.get("videos"))?null:PARSER.fromJson((String)dbObject.get("videos"),List.class));
        postInfo.setMusics("[]".equals(dbObject.get("musics"))?null:PARSER.fromJson((String)dbObject.get("musics"),List.class));
        postInfo.setOthers("[]".equals(dbObject.get("others"))?null:PARSER.fromJson((String)dbObject.get("others"),List.class));

        try{
            postInfo.setCreateDate(DateUtil.parseDate((String)dbObject.get("createDate")));
        } catch (DateParseException dpe){
            postInfo.setCreateDate(new Date());
        }

        try{
            postInfo.setPublishDate(DateUtil.parseDate((String)dbObject.get("publishDate")));
        } catch (DateParseException dpe){
            postInfo.setPublishDate(new Date());
        }

        postInfo.setStatus(PostStatus.getPostStatus((String)dbObject.get("status")));
        postInfo.setLevel(PostLevel.getPostLevel((String)dbObject.get("level")));

    }
}
