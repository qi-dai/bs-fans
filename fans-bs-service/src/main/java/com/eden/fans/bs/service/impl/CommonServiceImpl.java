package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.request.LoginRequest;
import com.eden.fans.bs.domain.response.LoginResponse;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.domain.response.ResponseCode;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.service.ICommonService;
import org.apache.commons.lang.StringUtils;
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
        redisCache.set(Constant.REDIS.VALID_TIME+timestamp, validCode,Constant.REDIS.VALID_CODE_TIMES);//验证码有效时间5分钟
    }

    @Override
    public boolean checkValidCode(String timestamp,String validCode) {
        String sourceCode = redisCache.get(Constant.REDIS.VALID_TIME+timestamp);
        logger.error("validCode:{},sourceCode:{}",validCode,sourceCode);
        if(validCode==null){
            //传入验证码为空，
            return false;
        }
        if (sourceCode==null){
            //验证码不存在或已失效，都校验失败
            return false;
        }
        validCode = validCode.toUpperCase();
        return sourceCode.toUpperCase().equals(validCode);
    }

    @Override
    public ServiceResponse<LoginResponse> checkUserInfo(LoginRequest loginRequest) {
        ServiceResponse<LoginResponse> serviceResponse = null;
        LoginResponse loginResponse = new LoginResponse();
        try{
            /**0.获取用户输入错误密码次数，如大于3次以上，校验验证码*/
            String pwdErrorNum = redisCache.get(loginRequest.getPhone()+Constant.REDIS.PWD_ERROR_NUM);
            int errorCount = 0;
            if(StringUtils.isNotBlank(pwdErrorNum)&&(errorCount = Integer.parseInt(pwdErrorNum))>=3){
                /**输入密码错误次数达到3次，需校验验证码*/
                boolean validFlag = checkValidCode(loginRequest.getTimestamp(),loginRequest.getValidCode());
                if(!validFlag){
                    /**验证码错误直接返回*/
                    serviceResponse = new ServiceResponse<LoginResponse>(ResponseCode.VALIDCODE_CHECK_FAILED);
                     return serviceResponse;
                }
            }

            /**1.查询用户信息*/
            UserVo userVo = redisCache.get(loginRequest.getPhone(),UserVo.class);
            if(userVo==null){
                //redis没有命中则查询数据库,根据手机号查询用户信息
                UserVo loginUserQry = new UserVo();
                loginUserQry.setPhone(loginRequest.getPhone());
                userVo = userDao.qryUserVo(loginUserQry);
                if (userVo!=null){
                    //手机号为主键，将用户信息放入缓存
                    redisCache.set(loginRequest.getPhone(),userVo);
                }
            }

            /**2.用户名不存在*/
            if(userVo==null){
                logger.error("没有查询到用户信息{}",loginRequest.getPhone());
                serviceResponse = new ServiceResponse<LoginResponse>(ResponseCode.LOGIN_CHECK_FAILED);
                return serviceResponse;
            }

            /**3.用户存在，拿到密码再次加密MD5，验证*/
            if(!userVo.getPassword().equals(MD5Util.md5(loginRequest.getPassword(), Constant.MD5_KEY))){
                //2.1 密码验证不匹配，记录错误次数+1到redis，两天后登录限制失效。
                errorCount++;
                redisCache.set(loginRequest.getPhone()+Constant.REDIS.PWD_ERROR_NUM,String.valueOf(errorCount));
                loginResponse.setErrorNum(errorCount);
                loginResponse.setIsSuccess(false);
                serviceResponse = new ServiceResponse<LoginResponse>(ResponseCode.LOGIN_CHECK_FAILED);
                serviceResponse.setResult(loginResponse);
                return serviceResponse;
            }
            /**4.验证通过，a存放用户信息到redis；b存放登录信息到redis*/
            String token = UUID.randomUUID().toString();
            token = token.replace("-","");

            //以TOKEN_手机号做主键，存放登录信息集合,多个设备登录有多个token
            StringBuffer tokensb = new StringBuffer();
            String tokenSrc = redisCache.get(Constant.REDIS.TOKEN+loginRequest.getPhone());//获取已登录tokens集合
            if(tokenSrc==null){
                //首次登录
                tokensb.append(token);
            }
            if (tokenSrc!=null){
                String[] arrayToken = tokenSrc.split("_");
                int tokenNum = 0;
                //登录超过十个，踢掉第一个登录的人
                if(arrayToken!=null&&(tokenNum = arrayToken.length)>=10){
                    for(int i=1;i<tokenNum;i++){
                        tokensb.append(arrayToken[i]);//从第二个开始，踢掉第一个
                        tokensb.append("_");
                    }
                    tokensb.append(token);//追加第十个
                }
                //登录未超过10个，直接追加记录token
                tokensb.append(tokenSrc);
                tokensb.append("_");
                tokensb.append(token);
            }
            redisCache.set(Constant.REDIS.TOKEN+loginRequest.getPhone(),tokensb.toString());//多个设备上登录，存放token集合，以_作为分隔符
            logger.error("生成唯一token:{},phone:{}", token, loginRequest.getPhone());
            serviceResponse = new ServiceResponse<LoginResponse>();
            loginResponse.setToken(token);
            loginResponse.setIsSuccess(true);
            serviceResponse.setResult(loginResponse);
            serviceResponse.setDetail("登录成功!");
            return serviceResponse;
        }catch (Exception e){
            logger.error("登录处理出错",e);
            serviceResponse = new ServiceResponse<LoginResponse>(ResponseCode.SYSTEM_ERROR);
        }finally {

        }
        return serviceResponse;
    }
}
