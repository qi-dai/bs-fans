package com.eden.fans.bs.dao;


import com.eden.fans.bs.domain.user.UserPraiseVo;

/**
 * Created by Administrator on 2016/6/5.
 */
public interface IUserPraiseDao {
    public boolean addUserPraise(UserPraiseVo userPraiseVo);
    public int sumUserSendPriase(Long userCode);
    public int sumUserReceivePriase(Long userCode);
    public int qryPraisedFlag(UserPraiseVo userPraiseVo);
}
