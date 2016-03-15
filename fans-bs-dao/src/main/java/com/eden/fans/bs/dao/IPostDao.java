package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.mvo.PostInfo;
import com.eden.fans.bs.domain.svo.ConcernUser;
import com.eden.fans.bs.domain.svo.PraiseUser;
import com.eden.fans.bs.domain.svo.ReplyPostInfo;

import java.util.List;

/**
 * 帖子有关操作定义
 *
 * Created by Administrator on 2016/3/15.
 */
public interface IPostDao {
    /**
     * 创建帖子
     */
    public boolean createPost(PostInfo postInfo);

    /**
     * 更新帖子状态（status）
     */
    public boolean updateStatus(String id,Integer status);

    /**
     *更新帖子的点赞用户列表（点赞、取消点赞）
     */
    public boolean updatePraiseUsers(String id, PraiseUser praiseUser);

    /**
     *更新帖子的关注用户列表（关注、取消关注）
     *
     */
    public boolean updateConcernUsers(String id, ConcernUser concernUser);

    /**
     * 根据帖子标志获取点赞用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     *
     */
    public Long countPraiseUsers();

    /**
     * 根据帖子标志获取关注用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     */
    public Long countConcernUsers();

    /**
     * 根据帖子的标志获取帖子下的点赞用户列表（分页获取，每页固定10条）
     */
    public List<ConcernUser> queryConcernUsersByPage(String id, Integer pageNum);

    /**
     * 根据帖子的标志获取帖子下关注的用户列表（分页获取，每页固定10条）
     */
    public List<PraiseUser> queryPraiseUsersByPage(String id, Integer pageNum);

    /**
     * 根据帖子的标志获取回帖信息列表（分页获取，每页固定10条）
     */
    public List<ReplyPostInfo> queryReplyPostInfosByPage(String id,Integer pageNum);

    /**
     * 根据帖子标志获取回帖用户总数（建议先读取缓存，缓存数据不存在是获取持久层的数据，并更新缓存）
     */
    public Long countReplyPostInfos();

}
