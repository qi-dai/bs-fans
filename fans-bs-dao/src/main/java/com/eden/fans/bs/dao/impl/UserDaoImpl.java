package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.UserVo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Mr.lee on 2016/3/15.
 */
@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public UserVo qryUserVoByPhonePWD(String phone, String pwd) {
        UserVo userVo = new UserVo();
        userVo.setPhone(phone);
        userVo.setPassword(pwd);
        return sqlSessionTemplate.selectOne("selectOneUserVo",userVo);
    }
}
