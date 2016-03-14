package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.ICardDao;
import com.eden.fans.bs.domain.Fans;
import com.mongodb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
@Repository
public class CardDaoImpl implements ICardDao{
    private static Logger logger = LoggerFactory.getLogger(CardDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void test() {
        Set<String> colls = this.mongoTemplate.getCollectionNames();
        for (String coll : colls) {
            logger.info("CollectionName=" + coll);
        }
        DB db = this.mongoTemplate.getDb();
        logger.info("db=" + db.toString());
    }

}
