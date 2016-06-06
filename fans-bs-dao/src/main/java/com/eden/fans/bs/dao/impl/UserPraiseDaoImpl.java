package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserPraiseDao;
import com.eden.fans.bs.domain.user.UserPraiseVo;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/6/5.
 */
@Repository
public class UserPraiseDaoImpl extends BaseDao<UserPraiseVo> implements IUserPraiseDao {
    @Override
    public boolean addUserPraise(UserPraiseVo userPraiseVo) {
        return super.insert("UserPraiseMapper.addPraiseRecord",userPraiseVo);
    }

    @Override
    public int sumUserSendPriase(Long userCode) {
        return super.queryCountForObject("UserPraiseMapper.qrySendPraiseCount",userCode);
    }

    @Override
    public int sumUserReceivePriase(Long userCode) {
        return super.queryCountForObject("UserPraiseMapper.qryReceivePraiseCount",userCode);
    }

    @Override
    public int qryPraisedFlag(UserPraiseVo userPraiseVo) {
        return super.queryCountForObject("UserPraiseMapper.qryPraisedFlag",userPraiseVo);
    }
}
