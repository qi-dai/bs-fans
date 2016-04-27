package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserCountDao;
import com.eden.fans.bs.domain.user.UserCountVo;
import com.eden.fans.bs.domain.user.UserVo;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/4/27.
 */
@Repository
public class UserCountDaoImpl extends BaseDao<UserCountVo> implements IUserCountDao {
    @Override
    public UserCountVo qryUserCountVo(UserVo userVo) {
        return super.queryForObject("UserMapper.queryUserCountVo",userVo);
    }
}
