package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.ICommonDao;
import com.eden.fans.bs.dao.IUserDao;
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
    ICommonDao commonDao;
    @Autowired
    IUserDao userDao;

    @Override
    public void saveValidCode(String validCode,String timestamp) {
        commonDao.saveKeyValue(validCode, timestamp);
    }

    @Override
    public boolean checkValidCode(String timestamp,String validCode) {
        String sourceCode = commonDao.getValue(timestamp);
        return sourceCode.equals(validCode);
    }

    @Override
    public String checkUserInfo(String phone,String pwd) {
        try{
            UserVo userVo = userDao.qryUserVoByPhonePWD(phone,pwd);
            if (userVo!=null&&userVo.getId()>0){
                String token = UUID.randomUUID().toString();
                //将用户信息，放入缓存-待做。
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
