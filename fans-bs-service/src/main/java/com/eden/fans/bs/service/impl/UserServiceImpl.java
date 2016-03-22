package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.dao.IOperUserDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.response.UserDetailResponse;
import com.eden.fans.bs.domain.user.UserOperVo;
import com.eden.fans.bs.domain.user.UserVo;
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
    private IUserDao userDao;//用户信息
    @Autowired
    private IOperUserDao operUserDao;//操作用户记录

    @Override
    public ServiceResponse<Boolean> addUserInfo(String phone,String password,String platform) {
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            //1.查询用户是否已存在
            UserVo userVo = new UserVo();//加密用户密码
            userVo.setPhone(phone);
            UserVo userVo1 = userDao.qryUserVo(userVo);
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
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            //1.查询用户是否已存在
            UserVo userVo1 = userDao.qryUserVo(userVo);
            if (userVo1!=null){
                serviceResponse = new ServiceResponse<Boolean>(ResponseCode.USER_EXIST_FAILED);
                return serviceResponse;
            }
            userVo.setPassword(MD5Util.md5(userVo.getPassword(), Constant.MD5_KEY));
            userVo.setActiveLevel(0);//等级默认是0
            userVo.setUserStatus(1);//状态默认 1 正常
            userVo.setUserRole("01");//角色，默认是01 普通用户
            boolean flag = userDao.addUserRecordDetail(userVo);
            serviceResponse = new ServiceResponse<Boolean>(flag);
            serviceResponse.setDetail("注册成功！");
        }catch (Exception e){
            logger.error("{}用户注册失败，操作数据库异常:{}",userVo.getPhone(),e);
            serviceResponse = new ServiceResponse<Boolean>(ResponseCode.ADD_USER_FAILED);
        }
        return serviceResponse;
    }


    @Override
    public ServiceResponse<UserDetailResponse> qryUserInfo(Long userCode) {
        ServiceResponse<UserDetailResponse> qryUserResponse = null;
        try{
            UserVo userVoQry = new UserVo();
            userVoQry.setUserCode(Long.valueOf(userCode));
            UserVo userVoResult = userDao.qryUserVo(userVoQry);
            UserDetailResponse detailResponse = new UserDetailResponse();
            detailResponse.setUserVo(userVoResult);
            if(userVoResult!=null){
                //查询其他详细,1.查询关注用户，2.查询被关注用户
                detailResponse.setAttentionNum(0);//Todo
                detailResponse.setFansNum(0);//Todo
            }else{
                qryUserResponse = new ServiceResponse<UserDetailResponse>(ResponseCode.QRY_USER_INFO_ERROR);
                return qryUserResponse;
            }
            qryUserResponse = new ServiceResponse<UserDetailResponse>();
            qryUserResponse.setResult(detailResponse);
            qryUserResponse.setDetail("查询用户详细信息成功");
        }catch (NumberFormatException e){
            logger.error("userCode:{}转换Integer出错！{}",userCode,e);
            qryUserResponse = new ServiceResponse<UserDetailResponse>(ResponseCode.USER_CODE_ERROR);
        }catch (Exception e){
            logger.error("查询{}用户信息出错！{}",userCode,e);
            qryUserResponse = new ServiceResponse<UserDetailResponse>(ResponseCode.QRY_USER_INFO_FAILED);
        }

        return qryUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> updateUserInfo(UserVo userVo) {
        ServiceResponse<Boolean> updateUserResponse = null;
        try{
            boolean updateFlag = userDao.updateUserRecord(userVo);
            if(!updateFlag){
                updateUserResponse = new ServiceResponse<Boolean>(ResponseCode.USER_NOTEXIST_ERROR);
                return updateUserResponse;
            }
            updateUserResponse = new ServiceResponse<Boolean>();
            updateUserResponse.setResult(true);
            updateUserResponse.setDetail("更新用户详细信息成功");
        }catch (Exception e){
            logger.error("更新{}用户信息出错！{}",userVo.getUserCode(),e);
            updateUserResponse = new ServiceResponse<Boolean>(ResponseCode.UPDATE_USER_INFO_FAILED);
        }
        return updateUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> setAdminRole(String adminUserCode, String targetUserCode) {
        ServiceResponse<Boolean> updateUserResponse = null;
        try{
            UserVo adminUser = new UserVo();
            adminUser.setUserCode(Long.valueOf(targetUserCode));
            adminUser.setUserRole("02");//管理员编码02
            boolean updateFlag = userDao.updateUserRecord(adminUser);
            if(!updateFlag){
                updateUserResponse = new ServiceResponse<Boolean>(ResponseCode.SET_ADMIN_ERROR);
                return updateUserResponse;
            }else{
                //增加操作记录，可降级，增加不成功也算设置成功。
                try{
                    UserOperVo userOperVo = new UserOperVo();
                    userOperVo.setOperCode(Long.valueOf(adminUserCode));
                    userOperVo.setUserCode(Long.valueOf(targetUserCode));
                    userOperVo.setOperDesc("设置管理员");
                    userOperVo.setOperType("05");//设置管理员
                    boolean flag = operUserDao.addOperUserRecord(userOperVo);
                    if(!flag){
                        logger.error("管理员：{},设置{}成管理员失败",adminUserCode,targetUserCode);
                    }
                }catch(Exception e){
                    logger.error("管理员：{},设置{}成管理员失败,操作数据库异常，",adminUserCode,targetUserCode,e);
                }
            }
            updateUserResponse = new ServiceResponse<Boolean>();
            updateUserResponse.setResult(true);
            updateUserResponse.setDetail("设置管理员成功");
        }catch (Exception e){
            logger.error("设置管理员出错{}！{}",targetUserCode,e);
            updateUserResponse = new ServiceResponse<Boolean>(ResponseCode.SET_ADMIN_FAILED);
        }
        return updateUserResponse;
    }
}
