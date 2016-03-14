package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.IFansDao;
import com.eden.fans.bs.domain.Fans;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Repository
public class FansDaoImpl implements IFansDao {
    private static Logger logger = LoggerFactory.getLogger(FansDaoImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static final String FANS_COLLECTION = "fans";
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 测试mysql
     */
    @Override
    public void test() {
        Fans fans = sqlSessionTemplate.selectOne("selectOneFans");
        logger.info("#selectOneFans# name:{}",fans.getFansNickName());
    }

    @Override
    public Fans findById(String key,Integer code) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(key)), Fans.class, FANS_COLLECTION + code);
    }

    @Override
    public void addFans(Fans fans,Integer code) {
        mongoTemplate.save(fans,FANS_COLLECTION + code);
    }

}
