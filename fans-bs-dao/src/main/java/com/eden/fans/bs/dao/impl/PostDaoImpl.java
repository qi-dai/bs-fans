package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.ICardDao;
import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Repository
public class PostDaoImpl implements IPostDao {
    private static Logger logger = LoggerFactory.getLogger(PostDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据帖子标识获取帖子
     *
     * @param appCode
     * @param id
     * @param keys
     */
    @Override
    public DBObject obtainPostById(String appCode, String id,BasicDBObject keys) {
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
        int result  = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).insert(post).getN();
        if(result>0){
            return true;
        }
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
        int result = this.mongoTemplate.getCollection(MongoConstant.POST_COLLECTION_PREFIX + appCode).update(new BasicDBObject("_id",new ObjectId(id)),new BasicDBObject("status",status)).getN();
        if(result>0){
            return true;
        }
        return false;
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
        Update update = new Update();
        update.set("praiseUsers.$.praise",praiseUser.getPraise());
        Query query = Query.query(Criteria.where("_id").is(id).and("praiseUsers.userCode").is(praiseUser.getUserCode()));

        int reslut = this.mongoTemplate.upsert(query,update,PostInfo.class,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        if(reslut>0)
            return true;
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
        Update update = new Update();
        update.set("concernUsers.$.concern",concernUser.getConcern());
        Query query = Query.query(Criteria.where("_id").is(id).and("concernUsers.userCode").is(concernUser.getUserCode()));

        int reslut = this.mongoTemplate.upsert(query,update,PostInfo.class,MongoConstant.POST_COLLECTION_PREFIX + appCode).getN();
        if(reslut>0)
            return true;
        return false;
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
