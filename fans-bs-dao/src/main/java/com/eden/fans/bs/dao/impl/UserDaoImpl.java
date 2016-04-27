package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.user.UserCountVo;
import com.eden.fans.bs.domain.user.UserVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.lee on 2016/3/15.
 */
@Repository
public class UserDaoImpl extends BaseDao<UserVo> implements IUserDao {
    @Override
    public UserVo qryUserVo(UserVo userVo) {
        return super.queryForObject("UserMapper.selectOneUserVo",userVo);
    }

    @Override
    public UserVo qryUserVoByPhone(String phone) {
        UserVo user = new UserVo();
        user.setPhone(phone);
        return super.queryForObject("UserMapper.selectOneUserVo",user);
    }

    @Override
    public UserVo qryUserVoByUserCode(Long userCode) {
        UserVo user = new UserVo();
        user.setUserCode(userCode);
        return super.queryForObject("UserMapper.selectOneUserVo",user);
    }


    @Override
    public boolean addUserRecord(UserVo userVo) {
        return super.insert("UserMapper.addUserInfo",userVo);
    }

    @Override
    public boolean addUserRecordDetail(UserVo userVo) {
        return super.insert("UserMapper.addUserInfoDetail",userVo);
    }

    @Override
    public boolean updateUserRecord(UserVo userVo,String sqlId) {
        return super.update(sqlId,userVo);
    }

    @Override
    public List<UserVo> qryUserVosBatch(Long... userCodes) {
        return super.queryForList("UserMapper.queryUserVoBatch",userCodes);
    }

    @Override
    public List<UserVo> qryUserVosBatch(Map<String, Object> params) {
        return super.queryForList("UserMapper.queryUserByPage",params);
    }

    @Override
    public int countTotalNum(Map<String, Object> params) {
        return super.queryCountForObject("UserMapper.countUserNum",params);
    }

}
