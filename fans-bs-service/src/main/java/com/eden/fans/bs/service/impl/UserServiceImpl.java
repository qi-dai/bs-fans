package com.eden.fans.bs.service.impl;

import cn.jpush.api.report.UsersResult;
import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.common.util.MD5Util;
import com.eden.fans.bs.common.util.UserUtils;
import com.eden.fans.bs.dao.*;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.request.*;
import com.eden.fans.bs.domain.response.*;
import com.eden.fans.bs.domain.user.AttentionVo;
import com.eden.fans.bs.domain.user.UserCountVo;
import com.eden.fans.bs.domain.user.UserOperVo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.ICommonService;
import com.eden.fans.bs.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ICommonService commonService;
    @Autowired
    private IUserCountDao userCountDao;
    @Autowired
    private IUserScoreDao userScoreDao;
    @Autowired
    private IAttentionDao attentionDao;
    @Autowired
    private IPostDao postDao;


    @Override
    public ServiceResponse<RegisterReponse> addUserInfo(RegisterRequest registerRequest) {
        ServiceResponse<RegisterReponse> serviceResponse;
        try{
            //0.校验验证码
            /**输入密码错误次数达到3次，需校验验证码*/
            boolean validFlag = commonService.checkValidCode(registerRequest.getTimestamp(), registerRequest.getValidCode());
            if(!validFlag){
                /**验证码错误直接返回*/
                serviceResponse = new ServiceResponse<RegisterReponse>(UserErrorCodeEnum.VALIDCODE_CHECK_FAILED);
                return serviceResponse;
            }
            //1.查询用户是否已存在
            UserVo userVo = new UserVo();//加密用户密码
            userVo.setPhone(registerRequest.getPhone());
            UserVo userVo1 = userDao.qryUserVo(userVo);
            if (userVo1!=null){
                serviceResponse = new ServiceResponse<RegisterReponse>(UserErrorCodeEnum.USER_EXIST_FAILED);
                return serviceResponse;
            }
            userVo.setPassword(MD5Util.md5(registerRequest.getPassword(), Constant.MD5_KEY));
            userVo.setPlatform(registerRequest.getPlatform());//使用平台
            userVo.setUserName(registerRequest.getPhone());//手机号码作为默认昵称
            userVo.setActiveLevel(0);//等级默认是0
            userVo.setUserStatus(1);//状态默认 1 正常
            userVo.setGender("un");//性别  unkown
            userVo.setUserRole("01");//角色，默认是01 普通用户
            boolean flag = userDao.addUserRecord(userVo);
            serviceResponse = new ServiceResponse<RegisterReponse>();
            if(flag){
                UserVo addedUser = userDao.qryUserVoByPhone(userVo.getPhone());
                RegisterReponse registerReponse = new RegisterReponse();
                registerReponse.setIsSuccess(true);
                registerReponse.setUserCode(addedUser.getUserCode());
                serviceResponse.setResult(registerReponse);
                serviceResponse.setDetail("注册成功！");
            }
        }catch (Exception e){
            logger.error("{}用户注册失败，操作数据库异常:{}",registerRequest.getPhone(),e);
            serviceResponse = new ServiceResponse<RegisterReponse>(UserErrorCodeEnum.ADD_USER_FAILED);
        }
        return serviceResponse;
    }
    /**
     * 用户填写详细资料注册service
     * */
    @Override
    public ServiceResponse<Boolean> addUserInfoDetail(UserVo userVo){
        ServiceResponse<Boolean> serviceResponse;
        try{
            //1.查询用户是否已存在
            UserVo userVo1 = userDao.qryUserVo(userVo);
            if (userVo1!=null){
                serviceResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.USER_EXIST_FAILED);
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
            serviceResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.ADD_USER_FAILED);
        }
        return serviceResponse;
    }


    @Override
    public ServiceResponse<UserDetailResponse> qryUserInfo(QryUserInfoRequest qryUserInfoRequest) {
        ServiceResponse<UserDetailResponse> qryUserResponse = null;
        try{
            //1.查询用户基本信息
            UserVo userVoQry = new UserVo();
            userVoQry.setUserCode(qryUserInfoRequest.getUserCode());
            UserVo userVoResult = userDao.qryUserVo(userVoQry);
            UserDetailResponse detailResponse = new UserDetailResponse();
            detailResponse.setUserVo(userVoResult);
            if(userVoResult!=null){
                //查询其他详细,1.查询关注用户，2.查询被关注用户,3.贡献人气，4.帖子数量，5.相册照片数量，6.视频数量，7.是否已关注
                try{
                    UserCountVo userCountVo = userCountDao.qryUserCountVo(userVoQry);
                    if(userCountVo!=null){
                        detailResponse.setAttentionNum(userCountVo.getAttentionNum());
                        detailResponse.setFansNum(userCountVo.getFansNum());
                        detailResponse.setImgNum(userCountVo.getImgNum());
                        detailResponse.setVideoNum(userCountVo.getVideoNum());
                    }
                }catch(Exception e){
                    logger.error("查询用户关注数量、多媒体数量出错",e);
                }
                try {
                    //查询贡献人气
                    detailResponse.setContributeScore(userScoreDao.sumUserScore(qryUserInfoRequest.getUserCode()));
                }catch (Exception e){
                    logger.error("查询用户贡献人气出错",e);
                }
                try {
                    //查询用户之间是否已关注,先判断是否是登录用户，是就查，不是直接默认没关注。
                    if (qryUserInfoRequest.getPhone() != null) {
                        AttentionVo attentionVo = new AttentionVo();
                        UserVo userInfo = getUserVo(qryUserInfoRequest.getPhone());
                        if (userInfo != null) {
                            attentionVo.setToUserCode(userInfo.getUserCode());
                        }
                        attentionVo.setFromUserCode(qryUserInfoRequest.getUserCode());
                        int isAtt = attentionDao.jadgeUserRelation(attentionVo);
                        detailResponse.setAttFlag(isAtt > 0 ? true : false);
                    }
                }catch (Exception e){
                    logger.error("查询用户之间是否已关注出错，",e);
                }

                try{
                    Long postCount = postDao.countPostByUserCode(qryUserInfoRequest.getAppCode(), qryUserInfoRequest.getUserCode());
                    if(postCount!=null){
                        detailResponse.setPostNum(postCount.intValue());
                    }
                }catch(Exception e){
                    logger.error("用户帖子总数查询出错",e);
                }

            }else{
                qryUserResponse = new ServiceResponse<UserDetailResponse>(UserErrorCodeEnum.QRY_USER_INFO_ERROR);
                return qryUserResponse;
            }
            qryUserResponse = new ServiceResponse<UserDetailResponse>();
            qryUserResponse.setResult(detailResponse);
            qryUserResponse.setDetail("查询用户详细信息成功");
        }catch (NumberFormatException e){
            logger.error("userCode:{}转换Integer出错！{}",qryUserInfoRequest.getUserCode(),e);
            qryUserResponse = new ServiceResponse<UserDetailResponse>(UserErrorCodeEnum.USER_CODE_ERROR);
        }catch (Exception e){
            logger.error("查询{}用户信息出错！{}",qryUserInfoRequest.getUserCode(),e);
            qryUserResponse = new ServiceResponse<UserDetailResponse>(UserErrorCodeEnum.QRY_USER_INFO_FAILED);
        }

        return qryUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> updateUserInfo(UserVo userVo) {
        ServiceResponse<Boolean> updateUserResponse;
        try{
            boolean updateFlag = userDao.updateUserRecord(userVo,"UserMapper.updateUserInfo");
            if(!updateFlag){
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.USER_NOTEXIST_ERROR);
                return updateUserResponse;
            }
            //刷新缓存
            redisCache.set(userVo.getPhone(),userDao.qryUserVo(userVo));
            updateUserResponse = new ServiceResponse<Boolean>();
            updateUserResponse.setResult(true);
            updateUserResponse.setDetail("更新用户详细信息成功");
        }catch (Exception e){
            logger.error("更新{}用户信息出错！{}",userVo.getUserCode(),e);
            updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.UPDATE_USER_INFO_FAILED);
        }
        return updateUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> setAdminRole(SetAdminRequest setAdminRequest) {
        ServiceResponse<Boolean> updateUserResponse;
        Long targetUserCode = setAdminRequest.getUserCode();
        try{
            //1.获取管理员信息
            UserVo userVo = redisCache.get(setAdminRequest.getPhone(), UserVo.class);
            Long adminUserCode = userVo.getUserCode();
            //2.更新用户角色
            UserVo adminUser = new UserVo();
            adminUser.setUserCode(setAdminRequest.getUserCode());
            if(setAdminRequest.getType()){
                adminUser.setUserRole("02");//管理员编码02
            }else{
                adminUser.setUserRole("01");//取消管理员权限
            }
            boolean updateFlag = userDao.updateUserRecord(adminUser,"UserMapper.setAdmin");
            if(!updateFlag){
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.SET_ADMIN_ERROR);
                return updateUserResponse;
            }
            //3.增加操作记录，可降级，增加不成功也算设置成功。
            try{
                UserOperVo userOperVo = new UserOperVo();
                userOperVo.setOperCode(adminUserCode);
                userOperVo.setUserCode(targetUserCode);
                userOperVo.setOperDesc("设置管理员");
                userOperVo.setOperType("05");//设置管理员
                boolean flag = operUserDao.addOperUserRecord(userOperVo);
                if(!flag){
                    logger.error("管理员：{},设置{}成管理员，添加操作记录失败",adminUserCode,targetUserCode);
                }
            }catch(Exception e){
                logger.error("管理员：{},设置{}成管理员，添加操作记录失败,操作数据库异常，",adminUserCode,targetUserCode,e);
            }
            //4.刷新redis缓存用户信息
            refreshRedisUserInfo(targetUserCode);
            //5.构建返回报文
            updateUserResponse = new ServiceResponse<Boolean>();
            updateUserResponse.setResult(true);
            updateUserResponse.setDetail("设置管理员成功");
        }catch (Exception e){
            logger.error("设置管理员出错{}！{}",targetUserCode,e);
            updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.SET_ADMIN_FAILED);
        }
        return updateUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> resetPwd(ResetPwdRequest resetPwdRequest) {
        ServiceResponse<Boolean> updateUserResponse = null;
        try{
            //1.校验验证码
            boolean validFlag = commonService.checkValidCode(resetPwdRequest.getTimestamp(), resetPwdRequest.getValidCode());
            if(!validFlag){
                /**验证码错误直接返回*/
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.VALIDCODE_CHECK_FAILED);
                updateUserResponse.setResult(false);
                return updateUserResponse;
            }
            //2.先校验原密码是否正确
            UserVo userVo = redisCache.get(resetPwdRequest.getPhone(),UserVo.class );
            if (userVo==null){
                UserVo qryUserVo = new UserVo();
                qryUserVo.setPhone(resetPwdRequest.getPhone());
                userVo = userDao.qryUserVo(qryUserVo);
            }
            if (userVo==null){
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.RESET_PWD_FAILED);
                return updateUserResponse;
            }
            if(!userVo.getPassword().equals(MD5Util.md5(resetPwdRequest.getOldPwd(), Constant.MD5_KEY))){
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.OLD_PWD_ERROR);
                return updateUserResponse;
            }

            //3.更新密码
            String  newPwd =MD5Util.md5(resetPwdRequest.getNewPwd(), Constant.MD5_KEY);
            UserVo pwdUser = new UserVo();
            pwdUser.setUserCode(resetPwdRequest.getUserCode());
            pwdUser.setPhone(resetPwdRequest.getPhone());
            pwdUser.setPassword(newPwd);
            boolean updateFlag = userDao.updateUserRecord(pwdUser,"UserMapper.resetPwd");
            if(!updateFlag){
                updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.RESET_PWD_ERROR);
                return updateUserResponse;
            }
            //4.刷新redis用户信息
            redisCache.set(userVo.getPhone(),userDao.qryUserVoByPhone(resetPwdRequest.getPhone()));
            redisCache.delete(Constant.REDIS.TOKEN+userVo.getPhone());//清空登录信息，所有设备需重新登录
            //5.构建返回报文
            updateUserResponse = new ServiceResponse<Boolean>();
            updateUserResponse.setResult(true);
            updateUserResponse.setDetail("设置密码成功");
        }catch (Exception e){
            logger.error("重设密码出错{}！{}",resetPwdRequest.getPhone(),e);
            updateUserResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.RESET_PWD_FAILED);
        }
        return updateUserResponse;
    }

    @Override
    public ServiceResponse<Boolean> updateUserStatus(StatusUpdateRequest statusUpdateRequest) {
        ServiceResponse<Boolean> statusResponse = null;
        Long targetUserCode = statusUpdateRequest.getUserCode();
        try {
            //1.获取管理员信息
            UserVo userVo = redisCache.get(statusUpdateRequest.getPhone(), UserVo.class);
            Long adminUserCode = userVo.getUserCode();
            //2.更新用户状态
            UserVo statusUser = new UserVo();
            statusUser.setUserCode(statusUpdateRequest.getUserCode());
            statusUser.setUserStatus(UserUtils.getUserStatus(statusUpdateRequest.getOperType()));//将操作类型转换为对应用户状态
            boolean updateFlag = userDao.updateUserRecord(statusUser,"UserMapper.updateUserStatus");
            if(!updateFlag){
                statusResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.UPDATE_USER_STATUS_ERROR);
                return statusResponse;
            }
            //3.增加操作记录，可降级，增加不成功也算设置成功。
            try{
                UserOperVo userOperVo = new UserOperVo();
                userOperVo.setOperCode(adminUserCode);
                userOperVo.setUserCode(targetUserCode);
                userOperVo.setOperDesc("更新用户状态"+statusUpdateRequest.getOperType());
                userOperVo.setOperType(statusUpdateRequest.getOperType());//设置管理员
                boolean flag = operUserDao.addOperUserRecord(userOperVo);
                if(!flag){
                    logger.error("管理员：{},设置用户{}失败",adminUserCode,targetUserCode);
                }
            }catch(Exception e){
                logger.error("管理员：{},设置用户{}状态失败,操作数据库异常，",adminUserCode,targetUserCode,e);
            }
            //4.更新redis缓存中，用户信息
            refreshRedisUserInfo(targetUserCode);
            //5.构建返回值
            statusResponse = new ServiceResponse<Boolean>();
            statusResponse.setResult(true);
            statusResponse.setDetail("设置用户状态成功");
        }catch (Exception e){
            logger.error("设置用户{}状态失败",statusUpdateRequest.getUserCode(),e);
            statusResponse = new ServiceResponse<Boolean>(UserErrorCodeEnum.UPDATE_USER_STATUS_FAILED);
        }
        return statusResponse;
    }

    /**
     * 刷新用户redis缓存
     * */
    private void refreshRedisUserInfo(Long userCode){
        UserVo userInfo =userDao.qryUserVoByUserCode(userCode);
        redisCache.set(userInfo.getPhone(),userInfo);
    }

    @Override
    public ServiceResponse<Boolean> freshUserInfo(String phone){
        UserVo userInfo =userDao.qryUserVoByPhone(phone);
        redisCache.set(userInfo.getPhone(),userInfo);
        return new ServiceResponse<Boolean>();
    }

    @Override
    public ServiceResponse<UserListResponse> qryUserVos(QryUserListRequest qryUserListRequest) {
        ServiceResponse<UserListResponse> response = null;
        try{
            Map<String,Object> params = new HashMap<String, Object>();
            Page page= new Page(qryUserListRequest.getPageNumber());
            page.setCurrentPage(qryUserListRequest.getCurrentPage());
            if("1".equals(qryUserListRequest.getQryType())){
                //根据角色查询，构建查询条件
                params.put("userRole",qryUserListRequest.getRole());
                int totalNumber = userDao.countTotalNum(params);
                page.setTotalNumber(totalNumber);
                params.put("page",page);
            }
            if("2".equals(qryUserListRequest.getQryType())){
                //根据用户状态查询，构建查询条件
                params.put("userStatus",qryUserListRequest.getStatus());
                int totalNumber = userDao.countTotalNum(params);
                page.setTotalNumber(totalNumber);
                params.put("page",page);
            }
            UserListResponse userListResponse = new UserListResponse();
            userListResponse.setPage(page);
            userListResponse.setUserVoList(userDao.qryUserVosBatch(params));
            response = new ServiceResponse<UserListResponse>(userListResponse);
            return response;
        }catch(Exception e){
            logger.error("查询用户列表失败",e);
            response = new ServiceResponse(UserErrorCodeEnum.QRY_USER_LIST_ERROR);
        }
        return response;
    }

    @Override
    public UserVo getUserVo(String phone) {
        UserVo  userInfo = redisCache.get(phone, UserVo.class);
        if (userInfo==null) userInfo =userDao.qryUserVoByPhone(phone);
        return userInfo;
    }


}