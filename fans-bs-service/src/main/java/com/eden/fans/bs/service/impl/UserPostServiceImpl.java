package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.GsonUtil;
import com.eden.fans.bs.dao.IUserPostDao;
import com.eden.fans.bs.domain.svo.ConcernPost;
import com.eden.fans.bs.domain.svo.PraisePost;
import com.eden.fans.bs.service.IUserPostService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/3/26
 * Time: 9:40
 * To change this template use File | Settings | File Templates.
 */

/**
 * 用户维度操作帖子（查询，点赞，关注）
 */
@Service
public class UserPostServiceImpl implements IUserPostService{

    private static final Logger logger = LoggerFactory.getLogger(UserPostServiceImpl.class);
    private static Gson PARSER = GsonUtil.getGson();

    @Autowired
    private IUserPostDao userPostDao;

    /**
     * 用户对某一个帖子添加关注（或取消关注）
     *
     * @param appCode
     * @param userCode
     * @param userName
     * @param concernPost
     */
    @Override
    public boolean concernPost(String appCode, Long userCode, String userName, ConcernPost concernPost) {
        concernPost.setTime(new Date());
        return userPostDao.concernPost(appCode, userCode, userName, concernPost);
    }

    /**
     * 用户对某一个帖子点赞（或取消点赞）
     *
     * @param appCode
     * @param userCode
     * @param userName
     * @param praisePost
     */
    @Override
    public boolean praisePost(String appCode, Long userCode, String userName, PraisePost praisePost) {
        praisePost.setTime(new Date());
        return false;
    }

    /**
     * 统计用户关注的帖子数
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public Long countConcernPost(String appCode, Long userCode) {
        return userPostDao.countConcernPost(appCode, userCode);
    }

    /**
     * 统计用户点赞的帖子数
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public Long countPraisePost(String appCode, Long userCode) {
        return userPostDao.countPraisePost(appCode, userCode);
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
        if(null == pageNum||pageNum<0)
            pageNum=0;
        return userPostDao.queryConcernPostByPage(appCode, userCode, pageNum);
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
        if(null == pageNum||pageNum<0)
            pageNum=0;
        return userPostDao.queryPraisePostByPage(appCode, userCode, pageNum);
    }

    /**
     * 获取用户关注的所有贴子
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public String queryAllConcernPost(String appCode, Long userCode) {
        return userPostDao.queryAllConcernPost(appCode, userCode);
    }

    /**
     * 获取用户点赞的所有贴子
     *
     * @param appCode
     * @param userCode
     */
    @Override
    public String queryAllPraisePost(String appCode, Long userCode) {
        return userPostDao.queryAllPraisePost(appCode, userCode);
    }
}
