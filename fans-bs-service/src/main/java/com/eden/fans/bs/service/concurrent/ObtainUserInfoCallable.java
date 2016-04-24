package com.eden.fans.bs.service.concurrent;

import com.eden.fans.bs.dao.IPostDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.user.UserVo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/4/24
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class ObtainUserInfoCallable implements Callable<Map<String,String>>{
    private Long userCode;
    private String postId;
    private IUserDao userDao;
    @Override
    public Map<String,String> call() throws Exception {
        Map<String,String> map = new HashMap<String, String>(4);
        UserVo userVo = new UserVo();
        userVo = userDao.qryUserVoByUserCode(userCode);
        map.put("postId",postId);
        map.put("userCode",userCode+"");
        map.put("headImgUrl",userVo.getHeadImgUrl());
        map.put("userName",userVo.getUserName());
        return map;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
}
