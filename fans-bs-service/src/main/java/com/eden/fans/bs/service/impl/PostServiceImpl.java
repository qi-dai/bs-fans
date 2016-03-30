package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.dao.IUserPostDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.*;
import com.eden.fans.bs.service.IPostService;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedHashTreeMap;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class PostServiceImpl implements IPostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private static Gson PARSER = GsonUtil.getGson();

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
    public String obtainPostByPage(String appCode, Integer pageNum) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("[");
        String postCount = redisCache.get(Constant.REDIS.POST_COUNT_PREFIX + appCode);
        if(null == postCount){
            logger.warn("帖子的数量在缓存中不存在 请检查缓存设置~");
            Long count = postDao.countPost(appCode);
            postCount = count + "";
            redisCache.set(Constant.REDIS.POST_COUNT_PREFIX+appCode,postCount);
        }

        if(null == pageNum || pageNum < 0)
            pageNum = 0;

        List<DBObject> dbObjectList = postDao.obtainPostByPage(appCode, pageNum);
        postHead2String(stringBuilder,dbObjectList,postCount);
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
        String postCount = redisCache.get(Constant.REDIS.USER_POST_COUNT_PREFIX + userCode + "_" + appCode);
        if(null == postCount){
            logger.warn("用户的数量在缓存中不存在 请检查缓存设置~");
            Long count = postDao.countPostByUserCode(appCode,userCode);
            postCount = count + "";
            redisCache.set(Constant.REDIS.USER_POST_COUNT_PREFIX + userCode + "_" + appCode,postCount);
        }

        if(null == pageNum || pageNum < 0)
            pageNum = 0;

        List<DBObject> dbObjectList = postDao.obtainPostByUserCode(appCode, userCode, pageNum);
        postHead2String(stringBuilder,dbObjectList,postCount);
        logger.info("根据用户分页获取帖子列表:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public String obtainPostById(String appCode, String postId) {
        DBObject object = postDao.obtainPostById(appCode,postId);
        if(null != object){
            StringBuilder stringBuilder = new StringBuilder();
            postDbObject2String(object,stringBuilder);
            return stringBuilder.toString();
        } else {
            return null;
        }
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
        boolean result =  postDao.createPost(appCode, dbObject);
        if(result){
            Long count = 0L;
            boolean postCountExists = redisCache.exists(Constant.REDIS.POST_COUNT_PREFIX + appCode);
            if(postCountExists){
                logger.warn("回帖信息的数量在缓存中不存在 请检查缓存设置~");
                count = postDao.countPost(appCode);
                redisCache.set(Constant.REDIS.POST_COUNT_PREFIX + appCode,count + "");
            }

            redisCache.incr(Constant.REDIS.USER_POST_COUNT_PREFIX + postInfo.getUserCode() + "_" + appCode);
            redisCache.incr(Constant.REDIS.POST_COUNT_PREFIX + appCode);
        }
        return result;
    }

    /**
     * 更新帖子状态（status）
     *
     * @param appCode
     * @param postId
     * @param status
     */
    @Override
    public boolean updateStatus(String appCode, String postId, PostStatus status) {
        return postDao.updateStatus(appCode, postId, status);
    }

    /**
     * 更新帖子的点赞用户列表（点赞、取消点赞）
     *
     * @param appCode
     * @param postId
     * @param praiseUser
     */
    @Override
    public boolean updatePraiseUsers(String appCode, String postId, PraiseUser praiseUser) {
        boolean result = postDao.updatePraiseUsers(appCode, postId, praiseUser);
        if(result){
            DBObject object = postDao.obtainPostById(appCode,postId);
            Map<String,Object> praisePostMap = new LinkedHashMap<String, Object>();
            praisePostMap.put("postId",postId);
            praisePostMap.put("title",object.get("title"));
            praisePostMap.put("status",praiseUser.getPraise().getValue());
            praisePostMap.put("time",new Date());

            Long count = 0L;
            boolean postCountExists = redisCache.exists(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode);
            if(postCountExists){
                logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
                count = postDao.countPraiseUsers(appCode,postId);
                redisCache.set(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode,count + "");
            } else {
                if(1 == praiseUser.getPraise().getValue()){
                    redisCache.incr(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode);
                    redisCache.incr(Constant.REDIS.USER_PRAISE_POST_COUNT_PREFIX + appCode);
                } else {
                    redisCache.decr(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode);
                    redisCache.decr(Constant.REDIS.USER_PRAISE_POST_COUNT_PREFIX + appCode);
                }
            }
            userPostDao.praisePost(appCode,praiseUser.getUserCode(),praiseUser.getUserName(),praisePostMap);
        }

        return  result;
    }

    /**
     * 更新帖子的关注用户列表（关注、取消关注）
     *
     * @param appCode
     * @param postId
     * @param concernUser
     */
    @Override
    public boolean updateConcernUsers(String appCode, String postId, ConcernUser concernUser) {
        boolean result = postDao.updateConcernUsers(appCode, postId, concernUser);

        if(result){
            DBObject object = postDao.obtainPostById(appCode,postId);
            Map<String,Object> concernPostMap = new LinkedHashMap<String, Object>();
            concernPostMap.put("postId",postId);
            concernPostMap.put("title",object.get("title"));
            concernPostMap.put("status",concernUser.getConcern().getValue());
            concernPostMap.put("time",new Date());

            Long count = 0L;
            boolean postCountExists = redisCache.exists(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode);
            if(postCountExists){
                logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
                count = postDao.countConcernUsers(appCode,postId);
                redisCache.set(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode,count + "");
            } else {
                if(1 == concernUser.getConcern().getValue()){
                    redisCache.incr(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode);
                    redisCache.incr(Constant.REDIS.USER_CONCERN_POST_COUNT_PREFIX + appCode);
                } else {
                    redisCache.decr(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode);
                    redisCache.decr(Constant.REDIS.USER_CONCERN_POST_COUNT_PREFIX + appCode);
                }
            }
            userPostDao.concernPost(appCode,concernUser.getUserCode(),concernUser.getUserName(),concernPostMap);
        }

        return  result;
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
        boolean result = postDao.updateReplyInfos(appCode, postId, replyPostInfo);
        if(result){
            boolean postCountExists = redisCache.exists(Constant.REDIS.POST_REPLY_COUNT_PREFIX +postId + "_" + appCode);
            if(postCountExists){
                Long count = 0L;
                logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
                count = postDao.countReplyPostInfos(appCode,postId);
                redisCache.set(Constant.REDIS.POST_REPLY_COUNT_PREFIX +postId + "_" + appCode,count + "");
            } else {
                redisCache.incr(Constant.REDIS.POST_REPLY_COUNT_PREFIX +postId + "_" + appCode);
            }
        }
        return result;
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
        Long count = 0L;
        String postCount = redisCache.get(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode);
        if(null == postCount){
            logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
            count = postDao.countConcernUsers(appCode,postId);
            postCount = count+"";
            redisCache.set(Constant.REDIS.POST_CONCERN_COUNT_PREFIX +postId + "_" + appCode,count + "");
        }

        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryConcernUsersByPage(appCode, postId, pageNum) + ",\"total\":"+postCount;
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
        Long count = 0L;
        String postCount = redisCache.get(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode);
        if(null == postCount){
            logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
            count = postDao.countPraiseUsers(appCode,postId);
            postCount = count + "";
            redisCache.set(Constant.REDIS.POST_PRAISE_COUNT_PREFIX +postId + "_" + appCode,count + "");
        }

        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryPraiseUsersByPage(appCode, postId,pageNum) + ",\"total\":" + postCount;
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
        Long count = 0L;
        String postCount = redisCache.get(Constant.REDIS.POST_REPLY_COUNT_PREFIX +postId + "_" + appCode);
        if(null == postCount){
            logger.warn("根据帖子的标志获取回帖信息的数量在缓存中不存在 请检查缓存设置~");
            count = postDao.countReplyPostInfos(appCode,postId);
            postCount = count + "";
            redisCache.set(Constant.REDIS.POST_REPLY_COUNT_PREFIX +postId + "_" + appCode,count + "");
        }

        if(null == pageNum || pageNum<0)
            pageNum = 0;
        return postDao.queryReplyPostInfosByPage(appCode, postId, pageNum) + ",\"total\":" + postCount;
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
        map.put("level",postInfo.getLevel());
        map.put("operatorList",postInfo.getOperatorList());
        map.put("concernUsers",postInfo.getConcernUsers());
        map.put("praiseUsers",postInfo.getPraiseUsers());
    }

    /**
     * 将帖子基本列表显示字段信息转成String
     * @param stringBuilder
     * @param dbObjectList
     * @param postCount
     */
    private void postHead2String(StringBuilder stringBuilder,List<DBObject> dbObjectList,String postCount){
        if(null != dbObjectList && dbObjectList.size()>0){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append("{[");
            for(DBObject dbObject:dbObjectList){
                stringBuilder.append("{");
                stringBuilder.append("\"postId\":\"" + dbObject.get("_id") + "\",");
                stringBuilder.append("\"title\":\"" + dbObject.get("title") + "\",");
                stringBuilder.append("\"createDate\":\"" + format.format((Date)dbObject.get("createDate")) + "\"");
                stringBuilder.append("},");
            }
            stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
            stringBuilder.append("],\"total\":" + postCount);
            stringBuilder.append("}");
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
        stringBuilder.append("\"createDate\":\"" + format.format((Date)dbObject.get("createDate")) + "\",");
        // 构建图片地址列表
        BasicDBList imgList = (BasicDBList)dbObject.get("imgs");
        if(null != imgList && imgList.size()>0){
            StringBuilder imgBuilder = new StringBuilder();
            imgBuilder.append("[");
            for(Object object:imgList){
                BasicDBObject basicDBObject = (BasicDBObject)object;
                imgBuilder.append("{");
                imgBuilder.append("\"index\":" + basicDBObject.get("index") + ",");
                imgBuilder.append("\"imgUrl\":\"" + basicDBObject.get("imgUrl") + "\"");
                imgBuilder.append("},");
            }
            imgBuilder.delete(imgBuilder.length()-1,imgBuilder.length());
            imgBuilder.append("]");
            stringBuilder.append("\"imgs\":\"" + imgBuilder + "\",");
        } else {
            stringBuilder.append("\"imgs\":[],");
        }
        // 构建视频地址列表
        BasicDBList videoList = (BasicDBList)dbObject.get("videos");
        if(null != videoList && videoList.size()>0){
            StringBuilder videoBuilder = new StringBuilder();
            videoBuilder.append("[");
            for(Object object:videoList){
                BasicDBObject basicDBObject = (BasicDBObject)object;
                videoBuilder.append("{");
                videoBuilder.append("\"index\":" + basicDBObject.get("index") + ",");
                videoBuilder.append("\"videoUrl\":\"" + basicDBObject.get("videoUrl") + "\"");
                videoBuilder.append("},");
            }
            videoBuilder.delete(videoBuilder.length()-1,videoBuilder.length());
            videoBuilder.append("]");
            stringBuilder.append("\"videos\":\"" + videoBuilder + "\",");
        } else {
            stringBuilder.append("\"videos\":[],");
        }

        // 构建音乐地址列表
        BasicDBList musicList = (BasicDBList)dbObject.get("musics");
        if(null != musicList && musicList.size()>0){
            StringBuilder musicBuilder = new StringBuilder();
            musicBuilder.append("[");
            for(Object object:musicList){
                BasicDBObject basicDBObject = (BasicDBObject)object;
                musicBuilder.append("{");
                musicBuilder.append("\"index\":" + basicDBObject.get("index") + ",");
                musicBuilder.append("\"musicUrl\":\"" + basicDBObject.get("musicUrl") + "\"");
                musicBuilder.append("},");
            }
            musicBuilder.delete(musicBuilder.length()-1,musicBuilder.length());
            musicBuilder.append("]");
            stringBuilder.append("\"musics\":\"" + musicBuilder + "\",");
        } else {
            stringBuilder.append("\"musics\":[],");
        }

        // 构建其他媒体列表
        BasicDBList otherList = (BasicDBList)dbObject.get("others");
        if(null != otherList && otherList.size()>0){
            StringBuilder otherBuilder = new StringBuilder();
            otherBuilder.append("[");
            for(Object object:otherList){
                BasicDBObject basicDBObject = (BasicDBObject)object;
                otherBuilder.append("{");
                otherBuilder.append("\"index\":" + basicDBObject.get("index") + ",");
                otherBuilder.append("\"otherUrl\":\"" + basicDBObject.get("otherUrl") + "\"");
                otherBuilder.append("},");
            }
            otherBuilder.delete(otherBuilder.length()-1,otherBuilder.length());
            otherBuilder.append("]");
            stringBuilder.append("\"others\":\"" + otherBuilder + "\",");
        } else {
            stringBuilder.append("\"others\":[],");
        }

        stringBuilder.append("\"createDate\":\"" + format.format((Date)dbObject.get("createDate")) + "\",");
        stringBuilder.append("\"publishDate\":\"" + format.format((Date)dbObject.get("publishDate")) + "\",");
        stringBuilder.append("\"status\":\"" + PostStatus.getName((Integer)dbObject.get("status")) + "\",");
        stringBuilder.append("\"level\":\"" + PostLevel.getName((Integer)dbObject.get("level")) + "\"");
        stringBuilder.append("}");
    }

}
