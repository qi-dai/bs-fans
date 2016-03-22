package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IOperUserDao;
import com.eden.fans.bs.domain.user.UserOperVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
@Repository
public class OperUserDaoImpl extends BaseDao<UserOperVo> implements IOperUserDao{

    @Override
    public List<UserOperVo> qryOperUserRecords(UserOperVo qryVo) {
        return super.queryForList("UserOperateMapper.selectUserOperRecords",qryVo);
    }

    @Override
    public boolean addOperUserRecord(UserOperVo addVo) {
        return super.insert("UserOperateMapper.addUserOperRecord",addVo);
    }
}
