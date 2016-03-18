package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.LoginInfo;
import com.eden.fans.bs.domain.UserVo;
import com.eden.fans.bs.service.ICommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Administrator on 2016/3/15.
 */
@Service
public class CommonServiceImpl implements ICommonService{
    private static Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private RedisCache redisCache;
    @Autowired
    IUserDao userDao;

    @Override
    public void saveValidCode(String validCode,String timestamp) {
        redisCache.set(validCode, timestamp);
    }

    @Override
    public boolean checkValidCode(String timestamp,String validCode) {
        String sourceCode = redisCache.get(timestamp);
        return sourceCode.equals(validCode);
    }

    @Override
    public String checkUserInfo(String phone,String pwd,String deviceId) {
        try{
            //拿到密码再次加密MD5
            UserVo userVo = userDao.qryUserVoByPhonePWD(phone,MD5Util.md5(pwd, Constant.MD5_KEY));
            if (userVo!=null&&userVo.getId()>0){
                String token = UUID.randomUUID().toString();
                token = token.replace("-","");
                //将用户信息，放入缓存
                redisCache.set(token,userVo);
                logger.error("生成唯一token:{},phone:{}",token,phone);
                return token;
            }
            logger.error("没有查询到用户信息{}",phone);
        }catch (Exception e){
            logger.error("处理出错",e);
        }finally {

        }
        return null;
    }
}
