package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.GsonEnumUtil;
import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.ICardDao;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Repository
public class PostDaoImpl implements IPostDao {
    private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);
    private static Gson PARSER = GsonEnumUtil.enumParseGson();
    @Autowired
    private MongoTemplate mongoTemplate;


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
        DBObject query = new BasicDBObject("userCode",userCode);
        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("title", 1);
        keys.put("createDate", 1);
        DBCursor cursor = null;
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).find(query,keys).skip(pageNum*10).limit(10);
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

        int result = this.mongoTemplate.updateFirst(query,update,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
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
        Query query = new BasicQuery(new BasicDBObject("$size","praiseUsers"),new BasicDBObject("_id",new ObjectId(postId)));
        return this.mongoTemplate.count(query,MongoConstant.POST_COLLECTION_PREFIX + appCode);
    }

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countConcernUsers(String postId,String appCode) {
        Query query = new BasicQuery(new BasicDBObject("$size","concernUsers"),new BasicDBObject("_id",new ObjectId(postId)));
        return this.mongoTemplate.count(query,MongoConstant.POST_COLLECTION_PREFIX + appCode);

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
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id).and("concernUsers.time").gt(time);
        query.addCriteria(criteria);
        query.limit(10);
        query.fields().include("concernUsers");
        // TODO
        return this.mongoTemplate.find(query,ConcernUser.class,MongoConstant.POST_COLLECTION_PREFIX + appCode);
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
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id).and("praiseUsers.time").gt(time);
        query.addCriteria(criteria);
        query.limit(10);
        query.fields().include("praiseUsers");
        // TODO
        return this.mongoTemplate.find(query,PraiseUser.class,MongoConstant.POST_COLLECTION_PREFIX + appCode);
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
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id).and("replyPostInfos.replyTime").gt(replyTime);
        query.addCriteria(criteria);
        query.limit(10);
        query.fields().include("replyPostInfos");
        // TODO
        return this.mongoTemplate.find(query,ReplyPostInfo.class,MongoConstant.POST_COLLECTION_PREFIX + appCode);
    }

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在时获取持久层的数据，并更新缓存）
     *
     * @param appCode
     */
    @Override
    public Long countReplyPostInfos(String postId,String appCode) {
        Query query = new BasicQuery(new BasicDBObject("$size","$replyPostInfos"),new BasicDBObject("_id",new ObjectId(postId)));
        return this.mongoTemplate.count(query,MongoConstant.POST_COLLECTION_PREFIX + appCode);
    }
}
