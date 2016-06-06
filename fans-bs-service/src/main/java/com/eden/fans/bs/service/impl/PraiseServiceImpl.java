package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IUserPraiseDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.request.PraiseRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.UserPraiseVo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.IPraiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/5.
 */
@Service
public class PraiseServiceImpl implements IPraiseService {
    private static final Logger logger = LoggerFactory.getLogger(PraiseServiceImpl.class);

    @Autowired
    private IUserPraiseDao userPraiseDao;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ServiceResponse<Boolean> setUserPraise(PraiseRequest praiseRequest) {
        ServiceResponse<Boolean> response= null;
        try{


            UserPraiseVo userPraiseVo = new UserPraiseVo();
            userPraiseVo.setToUserCode(praiseRequest.getToUserCode());
            userPraiseVo.setFromUserCode(redisCache.get(praiseRequest.getPhone(), UserVo.class).getUserCode());
            //赞之前，查询用户今日是否已对该用户赞过，如已赞过，则直接返回成功。
            int flag = userPraiseDao.qryPraisedFlag(userPraiseVo);
            if(flag>0){
                return new ServiceResponse<Boolean>();
            }
            response = new ServiceResponse<Boolean>(userPraiseDao.addUserPraise(userPraiseVo));
        }catch (Exception e){
            logger.error("用户对用户点赞失败",e);
            return new ServiceResponse<Boolean>(false);
        }
        return response;
    }
}
