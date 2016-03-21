package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.domain.response.ResponseCode;
import com.eden.fans.bs.domain.response.ServiceResponse;
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
    public void saveValidCode(String timestamp,String validCode) {
        redisCache.set(timestamp, validCode);
    }

    @Override
    public ServiceResponse<Boolean> checkValidCode(String timestamp,String validCode) {
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<Boolean>();
        String sourceCode = redisCache.get(timestamp);
        if(validCode==null){
            serviceResponse.setResult(false);
        }else {
            validCode = validCode.toUpperCase();
            serviceResponse.setResult(sourceCode.toUpperCase().equals(validCode));
        }
        logger.error("validCode:{},sourceCode:{}",validCode,sourceCode);
        return serviceResponse;
    }

    @Override
    public ServiceResponse<String> checkUserInfo(String phone,String pwd,String deviceId) {
        ServiceResponse<String> serviceResponse = null;
        try{
            //拿到密码再次加密MD5
            UserVo userVo = userDao.qryUserVo(new UserVo(phone,MD5Util.md5(pwd, Constant.MD5_KEY)));
            if (userVo!=null&&userVo.getId()>0){
                String token = UUID.randomUUID().toString();
                token = token.replace("-","");
                //1.以手机号为主键，将用户信息，放入缓存
                redisCache.set(phone,userVo);
                //2.以手机号_token做主键，存放登录信息集合
                StringBuffer tokensb = new StringBuffer();
                String tokenSrc = redisCache.get(phone+Constant.REDIS.TOKEN);//获取已登录tokens集合
                if (tokenSrc!=null){
                    tokensb.append(tokenSrc);
                    tokensb.append("_");
                }
                tokensb.append(token);
                redisCache.set(phone+Constant.REDIS.TOKEN,tokensb.toString());//多个设备上登录，存放token集合，以_作为分隔符
                logger.error("生成唯一token:{},phone:{}", token, phone);
                serviceResponse = new ServiceResponse(token);
                serviceResponse.setDetail("登录成功!");
            }else{
                logger.error("没有查询到用户信息{}",phone);
                serviceResponse = new ServiceResponse(ResponseCode.LOGIN_CHECK_FAILED);
            }
        }catch (Exception e){
            logger.error("处理出错",e);
        }finally {

        }
        return serviceResponse;
    }
}
