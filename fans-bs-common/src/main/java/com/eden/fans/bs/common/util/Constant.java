package com.eden.fans.bs.common.util;

/**
 * Created by shengyanpeng on 2016/3/3.
 */
public class Constant {
    public static final String MD5_KEY = "k9opz";
    public static final String ADMIN_ROLE_CODE = "02";
    public static class REDIS{
        public static final String VALID_TIME = "VALID_TIME_";
        public static final int VALID_CODE_TIMES=300;//验证码超时
        public static final String TOKEN = "TOKEN_";//登录认证token
        public static final String PWD_ERROR_NUM = "PWD_ERROR_NUM_";//密码错误次数
        // 用户、帖子维度操作相关 开始（主要是缓存分页相关的total）

        /**
         * 分页查询所有帖子count前缀
         */
        public static final String POST_COUNT_PREFIX="post_count_";

        /**
         * 分页查询用户发的帖子count前缀
         */
        public static final String USER_POST_COUNT_PREFIX="user_post_count_";
        public static final String MY_POST_COUNT_PREFIX="my_post_count_";

        /**
         * 分页查询帖子下关注用户count前缀
         */
        public static final String POST_CONCERN_COUNT_PREFIX="post_concern_count_";

        /**
         * 分页查询帖子下点赞用户count前缀
         */
        public static final String POST_PRAISE_COUNT_PREFIX="post_praise_count_";


        /**
         *分页查询回帖count前缀
         */
        public static final String POST_REPLY_COUNT_PREFIX="post_reply_count_";

        /**
         *分页查询某用户关注的帖子count前缀
         */
        public static final String USER_CONCERN_POST_COUNT_PREFIX="user_concern_post_count_";

        /**
         *分页查询某用户点赞的帖子count前缀
         */
        public static final String USER_PRAISE_POST_COUNT_PREFIX="user_praise_post_count_";
        // 用户、帖子维度操作相关 结束
    }



}
