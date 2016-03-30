package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.IUserPostDao;
import com.eden.fans.bs.domain.mvo.UserPostInfo;
import com.eden.fans.bs.domain.svo.ConcernPost;
import com.eden.fans.bs.domain.svo.PraisePost;
import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/3/25
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class UserPostDaoImpl implements IUserPostDao {
    private static Logger logger = LoggerFactory.getLogger(UserPostDaoImpl.class);
    private static Gson PARSER = GsonUtil.getGson();
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 用户对某一个帖子添加关注（或取消关注）
     *
     * @param appCode
     * @param userCode
     * @param userName
     * @param concernPostMap
     */
    @Override
    public boolean concernPost(String appCode, Long userCode, String userName, Map<String,Object> concernPostMap) {
        boolean result = true;
        // 先检查用户是否点赞或者关注过帖子
        Query existsQuery = Query.query(Criteria.where("userCode").is(userCode));
        boolean exists = this.mongoTemplate.exists(existsQuery,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode);
        if(exists){
            // 检查用户是否对当前的帖子有过操作 用户如果有操作过当前的帖子就更新状态，否则插入一个新的关注的帖子。
            Update update = new Update();
            update.set("concerns.$.status",concernPostMap.get("status"));
            update.set("concerns.$.time",concernPostMap.get("time"));
            Query updateQuery = Query.query(Criteria.where("userCode").is(userCode).and("concerns.postId").is(concernPostMap.get("postId")));
            int updateResult = this.mongoTemplate.updateFirst(updateQuery,update,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).getN();
            if(updateResult<=0){
                Update insert = new Update();
                Update.AddToSetBuilder builder = insert.addToSet("concerns");
                builder.each(concernPostMap);
                Query  insertQuery = Query.query(Criteria.where("userCode").is(userCode));
                updateResult = this.mongoTemplate.upsert(insertQuery,insert,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).getN();
            }
            if(0 == updateResult)
                result = false;

        } else {
            List<Map<String,Object>> concerns = new ArrayList<Map<String, Object>>(1);
            concerns.add(concernPostMap);
            DBObject dbObject = new BasicDBObject();
            dbObject.put("userCode",userCode);
            dbObject.put("userName",userName);
            dbObject.put("concerns",concerns);
            int insertResult = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).insert(dbObject).getN();
            if(0 != insertResult)
                result = false;
        }
        return result;
    }

    /**
     * 用户对某一个帖子点赞（或取消点赞）
     *
     * @param appCode
     * @param userCode
     * @param userName
     * @param praisePostMap
     */
    @Override
    public boolean praisePost(String appCode, Long userCode, String userName, Map<String,Object> praisePostMap) {
        boolean result = true;
        // 先检查用户是否点赞或者关注过帖子
        Query existsQuery = Query.query(Criteria.where("userCode").is(userCode));
        boolean exists = this.mongoTemplate.exists(existsQuery,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode);
        if(exists){
            // 检查用户是否对当前的帖子有过操作 用户如果有操作过当前的帖子就更新状态，否则插入一个新的关注的帖子。
            Update update = new Update();
            update.set("praises.$.status",praisePostMap.get("status"));
            update.set("praises.$.time",praisePostMap.get("time"));
            Query updateQuery = Query.query(Criteria.where("userCode").is(userCode).and("praises.postId").is(praisePostMap.get("postId")));
            int updateResult = this.mongoTemplate.updateFirst(updateQuery,update,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).getN();
            if(updateResult<=0){
                Update insert = new Update();
                Update.AddToSetBuilder builder = insert.addToSet("praises");
                builder.each(praisePostMap);
                Query  insertQuery = Query.query(Criteria.where("userCode").is(userCode));
                updateResult = this.mongoTemplate.upsert(insertQuery,insert,MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).getN();
            }
            if(0 == updateResult)
                result = false;

        } else {
            List<Map<String,Object>> praises = new ArrayList<Map<String, Object>>(1);
            praises.add(praisePostMap);
            DBObject dbObject = new BasicDBObject();
            dbObject.put("userCode",userCode);
            dbObject.put("userName",userName);
            dbObject.put("praises",praises);
            int insertResult = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).insert(dbObject).getN();
            if(0 != insertResult)
                result = false;
        }
        return result;
    }

    /**
     * 统计用户关注的帖子数
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public Long countConcernPost(String appCode, Long userCode) {
        DBObject object = new BasicDBObject();
        object.put("userCode",userCode);
        object.put("concerns.status",1);

        DBObject keys = new BasicDBObject();
        keys.put("concerns.status",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        UserPostInfo userPostInfo = PARSER.fromJson(JSON.serialize(postObject), UserPostInfo.class);
        return Long.valueOf(userPostInfo.getConcerns().size());
    }

    /**
     * 统计用户点赞的帖子数
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public Long countPraisePost(String appCode, Long userCode) {
        DBObject object = new BasicDBObject();
        object.put("userCode",userCode);
        object.put("praises.status",1);

        DBObject keys = new BasicDBObject();
        keys.put("praises.status",1);

        DBObject postObject = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == postObject){
            return 0L;
        }
        UserPostInfo userPostInfo = PARSER.fromJson(JSON.serialize(postObject), UserPostInfo.class);
        return Long.valueOf(userPostInfo.getPraises().size());
    }

    /**
     * 分页获取用户关注的帖子列表(分页参数 pageNum先从缓存获取，缓存不能用时从本地获取)
     *
     * @param appCode
     * @param userCode
     * @param pageNum
     */
    @Override
    public String queryConcernPostByPage(String appCode, Long userCode, Integer pageNum) {
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userCode",userCode);
        queryObject.put("concerns.status",1);

        DBObject sortObject = new BasicDBObject();
        sortObject.put("time",-1);

        DBObject keys = new BasicDBObject();
        keys.put("_id",1);
        keys.put("concerns", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));

        DBCursor cursor = null;
        StringBuilder concernPostString = new StringBuilder();
        concernPostString.append("[");
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).find(queryObject, keys).sort(sortObject);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                BasicDBList dbList = (BasicDBList)dbObject.get("concerns");
                concernOrPraise2String(concernPostString,dbList);
            }
        } catch (Exception e){
            logger.error("分页获取用户关注的帖子列表异常 MSG:{},ERROR:{}",e.getMessage(), Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return concernPostString.toString();
    }

    /**
     * 分页获取用户点赞的帖子列表
     *
     * @param appCode
     * @param userCode
     * @param pageNum
     */
    @Override
    public String queryPraisePostByPage(String appCode, Long userCode, Integer pageNum) {
        DBObject queryObject = new BasicDBObject();
        queryObject.put("userCode",userCode);
        queryObject.put("praises.status",1);

        DBObject sortObject = new BasicDBObject();
        sortObject.put("time",-1);

        DBObject keys = new BasicDBObject();
        keys.put("_id",1);
        keys.put("praises", new BasicDBObject("$slice", new Integer[]{pageNum*10, 10}));

        DBCursor cursor = null;
        StringBuilder praisePostString = new StringBuilder();
        praisePostString.append("[");
        try{
            cursor = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).find(queryObject, keys).sort(sortObject);
            if (cursor.hasNext()){
                DBObject dbObject =  cursor.next();
                BasicDBList dbList = (BasicDBList)dbObject.get("praises");
                concernOrPraise2String(praisePostString,dbList);
            }
        } catch (Exception e){
            logger.error("分页获取用户关注的帖子列表异常 MSG:{},ERROR:{}",e.getMessage(), Arrays.deepToString(e.getStackTrace()));
        }finally {
            if(null != cursor){
                cursor.close();
            }
        }
        praisePostString.append("]");
        return praisePostString.toString();
    }

    /**
     * 获取用户关注的所有贴子
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public String queryAllConcernPost(String appCode, Long userCode) {
        StringBuilder concernPostString = null;
        DBObject object = new BasicDBObject();
        object.put("userCode",appCode);
        object.put("concerns.status",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("concerns", 1);

        DBObject praisePost = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == praisePost){
            return "";
        }
        concernPostString = new StringBuilder();
        concernPostString.append("[");
        concernOrPraise2String(concernPostString,(BasicDBList) praisePost.get("praises"));
        concernPostString.append("]");
        return concernPostString.toString();
    }

    /**
     * 获取用户点赞的所有贴子
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public String queryAllPraisePost(String appCode, Long userCode) {
        StringBuilder praisePostString = null;
        DBObject object = new BasicDBObject();
        object.put("userCode",appCode);
        object.put("praises.status",1);

        DBObject keys = new BasicDBObject();
        keys.put("_id", 1);
        keys.put("praises", 1);

        DBObject praisePost = this.mongoTemplate.getCollection(MongoConstant.USER_POST_COLLECTION_PREFIX + appCode).findOne(object,keys);
        if(null == praisePost){
            return "";
        }
        praisePostString = new StringBuilder();
        praisePostString.append("[");
        concernOrPraise2String(praisePostString,(BasicDBList) praisePost.get("praises"));
        praisePostString.append("]");
        return praisePostString.toString();
    }

    /**
     * 将关注或者点赞的信息转成字符串
     * @param stringBuilder
     * @param dbList
     */
    private void concernOrPraise2String(StringBuilder stringBuilder,BasicDBList dbList){
        if(null != dbList && dbList.size()>0){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(Object object:dbList){
                BasicDBObject dbObject = (BasicDBObject)object;
                stringBuilder.append("{");
                stringBuilder.append("\"postId:\":\"" + dbObject.get("postId") +"\",");
                stringBuilder.append("\"title:\":\"" + dbObject.get("title") +"\",");
                stringBuilder.append("\"time:\":\"" + formatter.format((Date)dbObject.get("time")) +"\"");
                stringBuilder.append("},");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
    }
}
