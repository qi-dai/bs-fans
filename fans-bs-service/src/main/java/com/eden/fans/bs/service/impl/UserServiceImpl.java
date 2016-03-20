package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.UserVo;
import com.eden.fans.bs.domain.response.ResponseCode;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/20.
 */
@Service
public class UserServiceImpl implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public ServiceResponse<Boolean> addUserInfo(String phone,String password,String platform) {
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            //1.查询用户是否已存在

            UserVo userVo = new UserVo();//加密用户密码
            userVo.setPhone(phone);
            UserVo userVo1 = userDao.qryUserVoByPhonePWD(userVo);
            if (userVo1!=null){
                serviceResponse = new ServiceResponse<Boolean>(ResponseCode.USER_EXIST_FAILED);
                return serviceResponse;
            }
            userVo.setPassword(MD5Util.md5(password, Constant.MD5_KEY));
            userVo.setPlatform(platform);//使用平台
            userVo.setUserName(phone);//手机号码作为默认昵称
            userVo.setActiveLevel(0);//等级默认是0
            userVo.setUserStatus(1);//状态默认 1 正常
            userVo.setGender("un");//性别  unkown
            userVo.setUserRole("01");//角色，默认是01 普通用户
            boolean flag = userDao.addUserRecord(userVo);
            serviceResponse = new ServiceResponse<Boolean>(flag);
            serviceResponse.setDetail("注册成功！");
        }catch (Exception e){
            logger.error("{}用户注册失败，操作数据库异常:{}",phone,e);
            serviceResponse = new ServiceResponse<Boolean>(ResponseCode.ADD_USER_FAILED);
        }
        return serviceResponse;
    }
    /**
     * 用户填写详细资料注册service
     * */
    @Override
    public ServiceResponse<Boolean> addUserInfoDetail(UserVo userVo){
        return null;
    }
}