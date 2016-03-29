package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.svo.ConcernPost;
import com.eden.fans.bs.domain.svo.PraisePost;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/3/25
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public interface IUserPostDao {
    /**
     * 用户对某一个帖子添加关注（或取消关注）
     */
    public boolean concernPost(String appCode,Long userCode,String userName,Map<String,Object> concernPostMap);

    /**
     * 用户对某一个帖子点赞（或取消点赞）
     */
    public boolean praisePost(String appCode, Long userCode, String userName,Map<String,Object> praisePostMap);

    /**
     * 统计用户关注的帖子数
     */
    public Long countConcernPost(String appCode, Long userCode);

    /**
     * 统计用户点赞的帖子数
     */
    public Long countPraisePost(String appCode, Long userCode);

    /**
     * 分页获取用户关注的帖子列表(分页参数 pageNum先从缓存获取，缓存不能用时从本地获取)
     */
    public String queryConcernPostByPage(String appCode, Long userCode,Integer pageNum);

    /**
     * 分页获取用户点赞的帖子列表
     */
    public String queryPraisePostByPage(String appCode, Long userCode,Integer pageNum);

    /**
     * 获取用户关注的所有贴子
     */
    public String queryAllConcernPost(String appCode, Long userCode);

    /**
     * 获取用户点赞的所有贴子
     */
    public String queryAllPraisePost(String appCode, Long userCode);
}
