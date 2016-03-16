package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.ICommonDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/15.
 */
@Service
public class CommonServiceImpl implements ICommonService{

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
        return null;
    }
}
