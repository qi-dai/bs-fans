package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.google.gson.*;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Repository
public class PostDaoImpl implements IPostDao {
    private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);
    private static Gson PARSER = GsonUtil.getGson();
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 统计帖子的数目
     *
     * @param appCode
     * @return
     */
    @Override
    public Long countPost(String appCode) {
        DBObject query = new BasicDBObject("status",PostStatus.NORMAL.getValue());
        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).count(query);
    }

    /**
     * 根据用户标识统计帖子的数目
     *
     * @param appCode
     * @param userCode
     * @return
     */
    @Override
    public Long countPostByUserCode(String appCode, Long userCode) {
        DBObject query = new BasicDBObject();
        query.put("userCode",userCode);
        query.put("status",PostStatus.NORMAL.getValue());
        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).count(query);
    }
    @Override
    public Long countMyPost(String appCode, Long userCode) {
        DBObject query = new BasicDBObject();
        query.put("userCode",userCode);
        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).count(query);
    }

    /**
     * 获取待审批的帖子的数量
     *
     * @param appCode
     * @return
     */
    @Override
    public Long countApprovalPost(String appCode) {
        DBObject query = new BasicDBObject();
        query.put("status",PostStatus.CHECK.getValue());
        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).count(query);
    }

    /**
     * 根据用户标识获取已审批的帖子数量
     *
     * @param appCode
     * @param userCode
     * @return
     */
    @Override
    public Long countApprovedPost(String appCode, Long userCode) {
        DBObject query = new BasicDBObject();
        query.put("status",PostStatus.NORMAL.getValue());
        query.put("operatorList",userCode);
        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).count(query);
    }

    /**
     * 分页获取帖子
     *
     * @param appCode
     * @param pageNum
     * @return
     */
    @Override
    public List<DBObject> obtainPostByPage(String appCode,Integer postType,Integer pageNum) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);

        DBObject object = new BasicDBObject();
        object.put("status", PostStatus.NORMAL.getValue());
        object.put("type",postType.intValue());

        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);

        DBObject keys = new BasicDBObject();
        keys.put("title", 1);
        keys.put("userCode", 1);
        keys.put("createDate", 1);
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(new BasicDBObject(),keys).sort(sort).skip(pageNum * 10).limit(10);
            while (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                dbObjectList.add(dbObject);
            }
        } catch (Exception e){
            logger.error("根据用户标识获取帖子列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        } finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return dbObjectList;
    }

    /**
     * 根据用户标识获取帖子列表
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     */
    @Override
    public List<DBObject> obtainPostByUserCode(String appCode, Long userCode, Integer pageNum) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);

        DBObject query = new BasicDBObject("userCode",userCode);
        query.put("status",PostStatus.NORMAL.getValue());

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("userCode", 1);
        keys.put("createDate", 1);
        return userPostObject(appCode,pageNum,query,keys,sort);
    }

    @Override
    public List<DBObject> myPost(String appCode, Long userCode, Integer pageNum) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);

        DBObject query = new BasicDBObject("userCode",userCode);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("userCode", 1);
        keys.put("createDate", 1);
        keys.put("status", 1);
        return userPostObject(appCode, pageNum, query, keys, sort);
    }

    /**
     * 根据帖子标识获取帖子
     * @param appCode
     * @param postId
     */
    @Override
    public DBObject obtainPostById(String appCode, String postId) {
        //设置需要返回的属性列表(不包含点赞、关注和回帖用户列表)
        BasicDBObject keys = new BasicDBObject();
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

        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(new BasicDBObject("_id",new ObjectId(postId)),keys);
    }

    /**
     * 创建帖子
     *
     * @param appCode
     * @param post
     */
    @Override
    public boolean createPost(String appCode, DBObject post) {
        int result = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).insert(post).getN();
        if(0 == result)
            return true;
        return false;
    }

    /**
     * 更新帖子状态（status）
     *
     * @param appCode
     * @param status
     * @param postChecker
     */
    @Override
    public boolean updateStatus(String appCode, String postId, PostStatus status,Long postChecker) {
        Query query = Query.query(Criteria.where("_id").is(postId));
        Update update = new Update();
        update.set("status",status.getValue());
        update.set("publishDate", new Date());
        // 如果审批人不为空，则更新审批人列表
        if(null != postChecker){
            Update.AddToSetBuilder builder = update.addToSet("operatorList");
            builder.each(postChecker);
        }
        int result = this.mongoTemplate.updateFirst(query, update, MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        if(0 == result){
            return false;
        }
        return true;
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
        // 先更新内嵌文档
        Update update = new Update();
        update.set("praiseUsers.$.praise",praiseUser.getPraise().getValue());
        update.set("praiseUsers.$.time",new Date());
        Query query = Query.query(Criteria.where("_id").is(postId).and("praiseUsers.userCode").is(praiseUser.getUserCode()));
        int result = this.mongoTemplate.updateFirst(query,update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        // 更新失败插入
        if(result<=0){
            Map<String,Object> praiseUserMap = new LinkedHashMap<String,Object>();
            praiseUserMap.put("userCode",praiseUser.getUserCode());
            praiseUserMap.put("userName",praiseUser.getUserName());
            praiseUserMap.put("praise",praiseUser.getPraise().getValue());
            praiseUserMap.put("time",new Date());
            Update insert = new Update();
            Update.AddToSetBuilder builder = insert.addToSet("praiseUsers");
            builder.each(praiseUserMap);
            Query  insertQuery = Query.query(Criteria.where("_id").is(postId));
            result = this.mongoTemplate.upsert(insertQuery,insert,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        }
        if(0 == result)
            return false;
        return true;
    }

    /**
     * 更新回帖的列表
     *
     * @param appCode
     * @param postId
     * @param replyPostInfo
     * @return
     */
    @Override
    public boolean updateReplyInfos(String appCode, String postId, ReplyPostInfo replyPostInfo) {
        Map<String,Object> replyPostMap = new LinkedHashMap<String, Object>(4);
        replyPostMap.put("title",replyPostInfo.getTitle());
        replyPostMap.put("medias",replyPostInfo.getMedias());
        replyPostMap.put("content", replyPostInfo.getContent());
        replyPostMap.put("replyTime", replyPostInfo.getReplyTime());
        Query query = Query.query(Criteria.where("_id").is(postId));
        Update update = new Update();
        Update.AddToSetBuilder bulider = update.addToSet("replyPostInfos");
        bulider.each(replyPostMap);
        int result = this.mongoTemplate.updateFirst(query, update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        if(result>0)
            return true;
        return false;
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
        // 先更新内嵌文档
        Update update = new Update();
        update.set("concernUsers.$.concern", concernUser.getConcern().getValue());
        update.set("concernUsers.$.time", new Date());
        Query query = Query.query(Criteria.where("_id").is(postId).and("concernUsers.userCode").is(concernUser.getUserCode()));
        int result = this.mongoTemplate.updateFirst(query,update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        // 更新失败插入
        if(result<=0){
            Map<String,Object> concernUserMap = new LinkedHashMap<String, Object>();
            concernUserMap.put("userCode",concernUser.getUserCode());
            concernUserMap.put("userName",concernUser.getUserName());
            concernUserMap.put("concern",concernUser.getConcern().getValue());
            concernUserMap.put("time",new Date());
            Update insert = new Update();
            Update.AddToSetBuilder builder = insert.addToSet("concernUsers");
            builder.each(concernUserMap);
            Query  insertQuery = Query.query(Criteria.where("_id").is(postId));
            result = this.mongoTemplate.upsert(insertQuery,insert,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        }
        if(0 == result)
            return false;
        return true;

    }

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countPraiseUsers(String postId,String appCode) {
        DBObject object = new BasicDBObject();
        object.put("praiseUsers.praise", 1);
        object.put("_id", new ObjectId(postId));

        DBObject keys = new BasicDBObject();
        keys.put("praiseUsers.userCode",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        PostInfo postInfo = PARSER.fromJson(PARSER.toJson(postObject), PostInfo.class);
        return Long.valueOf(postInfo.getPraiseUsers().size());
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String postId,String appCode) {
        DBObject object = new BasicDBObject();
        object.put("_id", new ObjectId(postId));
        object.put("concernUsers.concern", 1);

        DBObject keys = new BasicDBObject();
        keys.put("concernUsers.userCode",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        PostInfo postInfo = PARSER.fromJson(PARSER.toJson(postObject),PostInfo.class);
        return Long.valueOf(postInfo.getConcernUsers().size());
    }

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countReplyPostInfos(String postId,String appCode) {
        DBObject object = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos.replyTime",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        PostInfo postInfo = PARSER.fromJson(PARSER.toJson(postObject), PostInfo.class);
        return Long.valueOf(postInfo.getConcernUsers().size());
    }

    /**
     * 获取所有关注的用户
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllConcernUsers(String appCode, String postId) {
        DBObject object = new BasicDBObject();
        object.put("_id",new ObjectId(postId));
        object.put("concernUsers.concern",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("concernUsers.userCode", 1);
        keys.put("concernUsers.userName", 1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return  "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        concernUserStringBuilder(stringBuilder,postObject,null);
        return stringBuilder.toString();
    }

    /**
     * 获取所有点赞的用户
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllPraiseUsers(String appCode, String postId) {
        DBObject object = new BasicDBObject();
        object.put("_id",new ObjectId(postId));
        object.put("praiseUsers.praise",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("praiseUsers.userCode", 1);
        keys.put("praiseUsers.userName", 1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        praiseUserStringBuilder(stringBuilder, postObject,null);
        return stringBuilder.toString();
    }

    /**
     * 获取所有回帖信息列表
     *
     * @param appCode
     * @param postId
     */
    @Override
    public String queryAllReplyPostInfos(String appCode, String postId) {
        DBObject object = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos", 1);
        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return  "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        replyInfoStringBuilder(stringBuilder, postObject,null);
        return stringBuilder.toString();
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryConcernUsersByPage(String appCode, String postId,Integer pageNum,Long total) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        DBObject queryObject = new BasicDBObject();
        queryObject.put("_id",new ObjectId(postId));
        queryObject.put("concernUsers.concern",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("concernUsers", new BasicDBObject("$slice", new Integer[]{pageNum * 10, 10}));

        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                concernUserStringBuilder(stringBuilder,dbObject,total);
            }
        } catch (Exception e){
            logger.error("根据帖子的标志获取帖子下的点赞用户列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryPraiseUsersByPage(String appCode, String postId,Integer pageNum,Long total) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        DBObject queryObject = new BasicDBObject();
        queryObject.put("_id",new ObjectId(postId));
        queryObject.put("praiseUsers.praise",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("praiseUsers", new BasicDBObject("$slice", new Integer[]{pageNum * 10, 10}));

        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                praiseUserStringBuilder(stringBuilder, dbObject,total);
            }
        } catch (Exception e){
            logger.error("根据帖子的标志获取帖子下关注的用户列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param postId
     * @param pageNum
     */
    @Override
    public String queryReplyPostInfosByPage(String appCode, String postId, Integer pageNum,Long total) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        DBObject queryObject = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));//new Integer[]{0,3}
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                replyInfoStringBuilder(stringBuilder, dbObject,total);
            }
        }catch (Exception e){
            logger.error("根据帖子的标志获取回帖信息列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 分页获取待审批的帖子列表
     *
     * @param appCode
     * @param pageNum
     * @param total
     * @return
     */

    /**
     * 分页获取待审批的帖子列表
     *
     * @param appCode
     * @param pageNum
     * @return
     */
    @Override
    public String queryApprovalPost(String appCode, Integer pageNum,Long total) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);

        DBObject query = new BasicDBObject("status",PostStatus.CHECK.getValue());

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("userCode", 1);
        keys.put("createDate", 1);
        dbObjectList = userPostObject(appCode,pageNum,query,keys,sort);
        
        StringBuilder stringBuilder = new StringBuilder();
        if(null != dbObjectList && dbObjectList.size()>0){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append("{\"data\":[");
            for(DBObject dbObject:dbObjectList){
                BasicDBObject object = (BasicDBObject)dbObject;
                stringBuilder.append("{");
                stringBuilder.append("\"postId\":\"" + object.get("_id") + "\",");
                stringBuilder.append("\"userCode\":\"" + object.get("userCode") + "\",");
                stringBuilder.append("\"title\":\"" + object.get("title") + "\",");
                stringBuilder.append("\"createDate\":\"" + formatter.format((Date)object.get("createDate")) + "\"");
                stringBuilder.append("},");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("],\"total\":" + total + "}");
        }
        
        return "";
    }

    /**
     * 根据用户标识分页获取已经审核通过的帖子列表
     *
     * @param appCode
     * @param userCode
     * @param pageNum
     * @return
     */
    @Override
    public List<DBObject> queryApprovedPost(String appCode, Long userCode, Integer pageNum) {

        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);

        DBObject query = new BasicDBObject("status",PostStatus.NORMAL.getValue());
        query.put("operatorList",userCode);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("userCode", 1);
        keys.put("createDate", 1);
        dbObjectList = userPostObject(appCode,pageNum,query,keys,sort);

        return dbObjectList;
    }

    /**
     * 构建关注用户字符串
     * @param stringBuilder
     * @param dbObject
     */
    private void concernUserStringBuilder(StringBuilder stringBuilder,DBObject dbObject,Long total){
        BasicDBList dbList = (BasicDBList)dbObject.get("concernUsers");
        stringBuilder.append("{\"data\":[");
        for(Object obj:dbList){
            BasicDBObject object = (BasicDBObject)obj;
            stringBuilder.append("{");
            stringBuilder.append("\"userCode\":\"" + object.get("userCode") + "\",");
            stringBuilder.append("\"userName\":\"" + object.get("userName") + "\"");
            stringBuilder.append("},");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("],\"total\":"+ (null == total?dbList.size():total) +"}");
    }

    /**
     * 构建点赞用户字符串
     * @param stringBuilder
     * @param dbObject
     */
    private void praiseUserStringBuilder(StringBuilder stringBuilder,DBObject dbObject,Long total){
        BasicDBList dbList = (BasicDBList)dbObject.get("praiseUsers");
        stringBuilder.append("{\"data\":[");
        for(Object obj:dbList){
            BasicDBObject object = (BasicDBObject)obj;
            stringBuilder.append("{");
            stringBuilder.append("\"userCode\":\"" + object.get("userCode") + "\",");
            stringBuilder.append("\"userName\":\"" + object.get("userName") + "\"");
            stringBuilder.append("},");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("],\"total\":" + (null == total?dbList.size():total) +"}");
    }

    /**
     * 构建回复符串
     * @param stringBuilder
     * @param replyDBObject
     */
    private void replyInfoStringBuilder(StringBuilder stringBuilder,DBObject replyDBObject,Long total){
        stringBuilder.append("{\"data\":[");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BasicDBList dbList = (BasicDBList)replyDBObject.get("replyPostInfos");
        for(Object obj:dbList){
            BasicDBObject dbObject = (BasicDBObject)obj;
            stringBuilder.append("{");
            stringBuilder.append("\"title\":\"" + dbObject.get("title") + "\",");

            BasicDBList mediaList = (BasicDBList)dbObject.get("medias");
            if(null != mediaList && mediaList.size()>0){
                StringBuilder mediaBuilder = new StringBuilder();
                mediaBuilder.append("[");
                for (Object meidaObj:mediaList){
                    mediaBuilder.append((String)meidaObj + ",");
                }
                mediaBuilder.deleteCharAt(mediaBuilder.length());
                mediaBuilder.append("]");
                stringBuilder.append("\"medias\":\"" + mediaBuilder + "\",");
            } else {
                stringBuilder.append("\"medias\":[],");
            }
            stringBuilder.append("\"replyTime\":\"" + formatter.format((Date) dbObject.get("replyTime")) + "\",");
            stringBuilder.append("\"content\":\"" + dbObject.get("content") + "\"");
            stringBuilder.append("},");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("],\"total\":"+ (null == total?dbList.size():total) +"}");
    }

    /**
     * 根据查询条件获取帖子列表
     * @param appCode
     * @param pageNum
     * @param query
     * @param keys
     * @param sort
     * @return
     */
    private List<DBObject> userPostObject(String appCode,Integer pageNum,DBObject query,DBObject keys,DBObject sort){
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(query,keys).sort(sort).skip(pageNum*10).limit(10);
            while (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                dbObjectList.add(dbObject);
            }
        } catch (Exception e){
            logger.error("根据用户标识获取帖子列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        } finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return dbObjectList;
    }

}
