package com.eden.fans.bs.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mybatis Dao.
 * Date: 14-7-08
 * Time: 下午3:22
 */
public class BaseDao<T> extends SqlSessionDaoSupport {

    /**
     * 解决Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required 错误
     * */
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 插入新记录
     * */
    public Boolean insert(String classMethod, T entity) {
        Boolean flag = (getSqlSession().insert(classMethod, entity) > 0);
        return flag;
    }

    /**
     * 更新单个实体的值
     * */
    public Boolean update(String classMethod, T entity) {
        Boolean flag = (getSqlSession().update(classMethod, entity) > 0);
        return flag;
    }

    /**
     * 带条件删除
     * */
    public Boolean delete(String classMethod, T entity) {
        Boolean flag = (getSqlSession().delete(classMethod, entity) > 0);
        return flag;
    }

    /**
     * 带条件查询统计总条数
     * */
    public Integer queryCountForObject(String classMethod, Object entity) {
        Object result = getSqlSession().selectOne(classMethod, entity);
        Integer count;
        try {
            count = (null == result) ? 0 : (Integer) result;
        } catch (ClassCastException e) {
            logger.error("sql语句：" + classMethod + "查询结果非数字类型，无法用于查询数量");
            throw e;
        }
        return count;
    }

    /**
     * 查询指定实体
     * */
    public T queryForObject(String classMethod, Object entity) {
        T result = (T) getSqlSession().selectOne(classMethod, entity);
        return result;
    }

    /**
     * 无查询条件执行查询sql （伪装无查询条件，或查询条件为常量，直接封装在sql语句中）
     * */
    public T queryForObjectNoEntity(String classMethod) {
        T result = (T) getSqlSession().selectOne(classMethod);
        return result;
    }

    /**
     * 没有查询参数entity可传null
     */
    public List queryForList(String classMethod, Object entity) {
        List result = getSqlSession().selectList(classMethod, entity);
        return result;
    }
}
