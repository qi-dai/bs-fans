package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IFansDao;
import com.eden.fans.bs.domain.Fans;
import com.eden.fans.bs.service.IFansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class FansServiceImpl implements IFansService {
    private static Logger logger = LoggerFactory.getLogger(FansServiceImpl.class);

    @Autowired
    private IFansDao fansDao;

    /**
     * mysql数据库测试
     */
    @Override
    public void test() {
        fansDao.test();
    }

    @Override
    public Fans findById(String key, Integer code) {
        return fansDao.findById(key,code);
    }

    @Override
    public void addFans(Fans fans, Integer code) {
        fansDao.addFans(fans,code);
    }
}
