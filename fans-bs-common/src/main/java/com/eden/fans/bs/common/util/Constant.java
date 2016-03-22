package com.eden.fans.bs.common.util;

/**
 * Created by shengyanpeng on 2016/3/3.
 */
public class Constant {
    public static final String MD5_KEY = "k9opz";
    public static class REDIS{
        public static final String VALID_TIME = "VALID_TIME_";
        public static final int VALID_CODE_TIMES=300;//验证码超时
        public static final String TOKEN = "TOKEN_";//登录认证token
        public static final String PWD_ERROR_NUM = "PWD_ERROR_NUM_";//密码错误次数
    }
}
