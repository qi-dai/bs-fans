package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
     * 分页获取帖子
     *
     * @param appCode
     * @param pageNum
     * @return
     */
    @Override
    public List<DBObject> obtainPostByPage(String appCode, Integer pageNum) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);
        DBObject keys = new BasicDBObject();
        keys.put("title", 1);
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
    public List<DBObject> obtainPostByUserCode(String appCode, Integer userCode, Integer pageNum) {
        List<DBObject> dbObjectList = new ArrayList<DBObject>(10);
        DBObject sort = new BasicDBObject();
        sort.put("createDate",-1);
        DBObject query = new BasicDBObject("userCode",userCode);
        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("createDate", 1);
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

    /**
     * 根据帖子标识获取帖子
     * @param appCode
     * @param id
     */
    @Override
    public DBObject obtainPostById(String appCode, String id) {
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

        return this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(new BasicDBObject("_id",new ObjectId(id)),keys);
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
     * @param id
     * @param status
     */
    @Override
    public boolean updateStatus(String appCode, String id, Integer status) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("status",status);
        update.set("publishDate", new Date().getTime());

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
     * @param id
     * @param praiseUser
     */
    @Override
    public boolean updatePraiseUsers(String appCode, String id, PraiseUser praiseUser) {
        // 先更新内嵌文档
        Update update = new Update();
        update.set("praiseUsers.$.praise",praiseUser.getPraise());
        update.set("praiseUsers.$.time",praiseUser.getTime().getTime());
        Query query = Query.query(Criteria.where("_id").is(id).and("praiseUsers.userCode").is(praiseUser.getUserCode()));
        int result = this.mongoTemplate.updateFirst(query,update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        // 更新失败插入
        if(result<=0){
            Update insert = new Update();
            Update.AddToSetBuilder builder = insert.addToSet("praiseUsers");
            builder.each(JSON.parse(PARSER.toJson(praiseUser)));
            Query  insertQuery = Query.query(Criteria.where("_id").is(id));
            result = this.mongoTemplate.upsert(insertQuery,insert,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        }
        if(0 == result)
            return false;
        return true;
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
        // 先更新内嵌文档
        Update update = new Update();
        update.set("concernUsers.$.concern",concernUser.getConcern());
        update.set("concernUsers.$.time",concernUser.getTime().getTime());
        Query query = Query.query(Criteria.where("_id").is(id).and("concernUsers.userCode").is(concernUser.getUserCode()));
        int result = this.mongoTemplate.updateFirst(query,update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        // 更新失败插入
        if(result<=0){
            Update insert = new Update();
            Update.AddToSetBuilder builder = insert.addToSet("concernUsers");
            builder.each(JSON.parse(PARSER.toJson(concernUser)));
            Query  insertQuery = Query.query(Criteria.where("_id").is(id));
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
        DBObject object = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("praiseUsers.userCode",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        return Long.valueOf(postInfo.getPraiseUsers().size());
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String postId,String appCode) {
        DBObject object = new BasicDBObject("_id",new ObjectId(postId));
        DBObject keys = new BasicDBObject();
        keys.put("concernUsers.userCode",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
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
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        return Long.valueOf(postInfo.getConcernUsers().size());
    }

    /**
     * 获取所有关注的用户
     *
     * @param appCode
     * @param id
     */
    @Override
    public List<ConcernUser> queryAllConcernUsers(String appCode, String id) {
        DBObject object = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("concernUsers.userCode", 1);
        keys.put("concernUsers.userName", 1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return  new ArrayList<ConcernUser>();
        }
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        return postInfo.getConcernUsers();
    }

    /**
     * 获取所有点赞的用户
     *
     * @param appCode
     * @param id
     */
    @Override
    public List<PraiseUser> queryAllPraiseUsers(String appCode, String id) {
        DBObject object = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("praiseUsers.userCode", 1);
        keys.put("praiseUsers.userName", 1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return  new ArrayList<PraiseUser>();
        }
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        return postInfo.getPraiseUsers();
    }

    /**
     * 获取所有回帖信息列表
     *
     * @param appCode
     * @param id
     */
    @Override
    public List<ReplyPostInfo> queryAllReplyPostInfos(String appCode, String id) {
        DBObject object = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos", 1);
        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return  new ArrayList<ReplyPostInfo>();
        }
        PostInfo postInfo = PARSER.fromJson(JSON.serialize(postObject), PostInfo.class);
        return postInfo.getReplyPostInfos();
    }

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param pageNum
     */
    @Override
    public List<ConcernUser> queryConcernUsersByPage(String appCode, String id,Integer pageNum) {
        DBObject queryObject = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("concernUsers", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));
        DBCursor cursor = null;
        List<ConcernUser> concernUsers = new ArrayList<ConcernUser>();
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                PostInfo postInfo = PARSER.fromJson(JSON.serialize(dbObject), PostInfo.class);
                concernUsers =  postInfo.getConcernUsers();
            }
        } catch (Exception e){
            logger.error("根据帖子的标志获取帖子下的点赞用户列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return concernUsers;
    }

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     *
     * @param appCode
     * @param id
     * @param pageNum
     */
    @Override
    public List<PraiseUser> queryPraiseUsersByPage(String appCode, String id,Integer pageNum) {
        DBObject queryObject = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("praiseUsers", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));
        DBCursor cursor = null;
        List<PraiseUser> concernUsers = new ArrayList<PraiseUser>();
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                PostInfo postInfo = PARSER.fromJson(JSON.serialize(dbObject), PostInfo.class);
                concernUsers =  postInfo.getPraiseUsers();
            }
        } catch (Exception e){
            logger.error("根据帖子的标志获取帖子下关注的用户列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return concernUsers;
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
        DBObject queryObject = new BasicDBObject("_id",new ObjectId(id));
        DBObject keys = new BasicDBObject();
        keys.put("replyPostInfos", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));//new Integer[]{0,3}
        DBCursor cursor = null;
        List<ReplyPostInfo> replyPostInfos = new ArrayList<ReplyPostInfo>();
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(queryObject, keys);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                PostInfo postInfo = PARSER.fromJson(JSON.serialize(dbObject), PostInfo.class);
                replyPostInfos =  postInfo.getReplyPostInfos();
            }
        }catch (Exception e){
            logger.error("根据帖子的标志获取回帖信息列表异常 MSG:{},ERROR:{}",e.getMessage(),Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return replyPostInfos;
    }

}
