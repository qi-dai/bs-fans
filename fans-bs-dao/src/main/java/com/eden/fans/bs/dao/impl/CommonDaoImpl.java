package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.common.util.MongoConstant;
import com.eden.fans.bs.dao.ICommonDao;
import com.eden.fans.bs.domain.Fans;
import com.eden.fans.bs.domain.ValidCodeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/15.
 */
@Repository
public class CommonDaoImpl implements ICommonDao{
    private static Logger logger = LoggerFactory.getLogger(CommonDaoImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void saveKeyValue(String key, String value) {
        mongoTemplate.save(new ValidCodeVo(key,value),MongoConstant.common.VALID_CODE);
    }

    @Override
    public String getValue(String key) {
        ValidCodeVo validCodeVo = mongoTemplate.findOne(new Query(Criteria.where("key").is(key)), ValidCodeVo.class, MongoConstant.common.VALID_CODE);
        if(validCodeVo==null){
            logger.error("验证码错误或已失效！");
            return null;
        }
        return validCodeVo.getValue();
    }
}
