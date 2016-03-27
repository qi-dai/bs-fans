package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Date;
import java.util.List;

/**
 * 帖子有关操作定义
 *
 * Created by Administrator on 2016/3/15.
 */
public interface IPostDao {


    /**
     * 统计帖子的数目
     * @param appCode
     * @return
     */
    public Long countPost(String appCode);

    /**
     * 根据用户标识统计帖子的数目
     * @param appCode
     * @return
     */
    public Long countPostByUserCode(String appCode,Long userCode);

    /**
     * 分页获取帖子
     * @param appCode
     * @param pageNum
     * @return
     */
    public List<DBObject> obtainPostByPage(String appCode,Integer pageNum);

    /**
     * 根据用户标识获取用户发的帖子列表
     * @param userCode
     * @param pageNum
     * @return
     */
    public List<DBObject> obtainPostByUserCode(String appCode,Integer userCode,Integer pageNum);

    /**
     * 根据帖子标识获取帖子
     *
     */
    public DBObject obtainPostById(String appCode,String id);

    /**
     * 创建帖子
     */
    public boolean createPost(String appCode,DBObject post);

    /**
     * 更新帖子状态（status）
     */
    public boolean updateStatus(String appCode,String id,PostStatus status);

    /**
     *更新帖子的点赞用户列表（点赞、取消点赞）
     */
    public boolean updatePraiseUsers(String appCode,String id, PraiseUser praiseUser);

    /**
     * 更新回帖的列表
     * @param appCode
     * @param id
     * @param replyPostInfo
     * @return
     */
    public boolean updateReplyInfos(String appCode,String id,ReplyPostInfo replyPostInfo);

    /**
     *更新帖子的关注用户列表（关注、取消关注）
     *
     */
    public boolean updateConcernUsers(String appCode,String id, ConcernUser concernUser);

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     *
     */
    public Long countPraiseUsers(String postId,String appCode);

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     */
    public Long countConcernUsers(String postId,String appCode);

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     */
    public Long countReplyPostInfos(String postId,String appCode);

    /**
     * 获取所有关注的用户
     */
    public String queryAllConcernUsers(String appCode,String id);

    /**
     * 获取所有点赞的用户
     */
    public String queryAllPraiseUsers(String appCode,String id);

    /**
     * 获取所有回帖信息列表
     */
    public String queryAllReplyPostInfos(String appCode,String id);

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     */
    public String queryConcernUsersByPage(String appCode,String id,Integer pageNum);

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     */
    public String queryPraiseUsersByPage(String appCode,String id,Integer pageNum);

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     */
    public String queryReplyPostInfosByPage(String appCode,String id,Integer pageNum);

}
