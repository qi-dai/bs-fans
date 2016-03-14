package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.ICardDao;
import com.eden.fans.bs.dao.IFansDao;
import com.eden.fans.bs.domain.Fans;
import com.eden.fans.bs.service.ICardService;
import com.eden.fans.bs.service.IFansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Service
public class CardServiceImpl implements ICardService {
    private static Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    private ICardDao cardDao;

    @Override
    public void test() {
        cardDao.test();
    }
}
