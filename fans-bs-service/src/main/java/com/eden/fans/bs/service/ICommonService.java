package com.eden.fans.bs.service;

/**
 * Created by Mr.lee on 2016/3/15.
 */
public interface ICommonService {
    /**
     * 保存验证码到缓存
     * @param timestamp 时间戳 key
     * @param validCode 四位数随机验证码 value
     * */
    public void saveValidCode(String timestamp,String validCode);
    /**
     * 验证验证码是否正确
     * @param timestamp 时间戳 key
     * @param validCode 四位数随机验证码 value
     * */
    public boolean checkValidCode(String timestamp,String validCode);
    /**
     * 验证用户登录信息是否正确
     * @param phone 手机号
     * @param pwd 密文
     * */
    public String checkUserInfo(String phone,String pwd);
}
