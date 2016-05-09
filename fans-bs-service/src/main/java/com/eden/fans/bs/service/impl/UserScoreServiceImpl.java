package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.dao.IUserScoreDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.request.ScoreRecordRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.response.SystemErrorEnum;
import com.eden.fans.bs.domain.user.UserScoreVo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.ICommonService;
import com.eden.fans.bs.service.IUserScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/7.
 */
@Service
public class UserScoreServiceImpl implements IUserScoreService {
    private static final Logger logger = LoggerFactory.getLogger(UserPostServiceImpl.class);

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IUserScoreDao userScoreDao;

    @Autowired
    private ICommonService commonService;


    /**
     * 积分种类及计算规则
     * 1：点赞，累计10次得2分；
     * -1：取消点赞，累计10次得-2分；
     * 2：回复（非本人），一次累计2分；
     * -2：删除回复，一次累计-2分；
     * 3：发帖审核通过，一次累计得5分；
     * -3：审核通过帖子被删除，一次累计得-5分；
     * 4：帖子被加精，一次累计3分；
     * -4：帖子被取消加精，一次累计-3分；
     * 5：帖子被置顶，一次累计加2分；
     * -5：帖子被取消置顶，一次累计加-2分；
     * 6：帖子被回复（非本人），累计十次得2分；
     * 7：被关注，累计一次得2分；
     * -7：被取消关注，累计一次得-2分；
     * 8：关注别人，累计一次得2分；
     * -8：取消关注别人，累计一次得-2分；
     * */
    @Override
    public void addUserScore(Long userCode, int scoreType) {
        try{
            UserScoreVo userScoreVo = new UserScoreVo(userCode,String.valueOf(scoreType),0);
            boolean flag = false;
            switch(scoreType) {
                case 1:
                    buildScoreVoByPraise(userScoreVo);
                    break;
                case -1:
                    buildScoreVoByUnPraise(userScoreVo);
                    break;
                case 2:
                    userScoreVo.setScore(2);
                    flag= true;
                    break;
                case -2:
                    userScoreVo.setScore(-2);
                    flag= true;
                    break;
                case 3:
                    userScoreVo.setScore(5);
                    flag= true;
                    break;
                case -3:
                    userScoreVo.setScore(-5);
                    flag= true;
                    break;
                case 4:
                    userScoreVo.setScore(3);
                    flag= true;
                    break;
                case -4:
                    userScoreVo.setScore(-3);
                    flag= true;
                    break;
                case 5:
                    userScoreVo.setScore(2);
                    flag= true;
                    break;
                case 6:
                    buildScoreVoByReply(userScoreVo);
                    break;
                case 7:
                    userScoreVo.setScore(2);
                    flag= true;
                    break;
                case -7:
                    userScoreVo.setScore(-2);
                    flag= true;
                    break;
                case 8:
                    userScoreVo.setScore(2);
                    flag= true;
                    break;
                case -8:
                    userScoreVo.setScore(-2);
                    flag= true;
                    break;
                case 9:
                    buildScoreVoByLogin(userScoreVo);
                    break;
            }
            if(flag) userScoreDao.addUserScore(userScoreVo);
        }catch (Exception e){
            logger.error("增加用户人气积分出错",e);
        }
    }

    @Override
    public ServiceResponse<Boolean> addScoreRecord(ScoreRecordRequest scoreRecordRequest) {
        ServiceResponse<Boolean> response = null;
        try{
            if(scoreRecordRequest.getScoreType()==9){
                UserVo userVo = commonService.qryUserVo(scoreRecordRequest.getPhone());
                scoreRecordRequest.getPhone();
                addUserScore(userVo.getUserCode(),9);
            }
            response = new ServiceResponse<Boolean>(true);
        }catch (Exception e){
            logger.error("计算人气积分出错，",e);
            response = new ServiceResponse<Boolean>(SystemErrorEnum.FAILED);
        }
        return response;
    }

    private void buildScoreVoByPraise(UserScoreVo userScoreVo){
        try{
            String num = redisCache.get(Constant.REDIS.SCORE_NUM_PRAISE+userScoreVo.getUserCode());
            int pnum = 0;
            if (num!=null){
                pnum = Integer.valueOf(num);
            }
            if(pnum==9){
                userScoreVo.setScore(2);
                userScoreDao.addUserScore(userScoreVo);
                redisCache.set(Constant.REDIS.SCORE_NUM_PRAISE+userScoreVo.getUserCode(),0);
            }else{
                redisCache.set(Constant.REDIS.SCORE_NUM_PRAISE+userScoreVo.getUserCode(),pnum+1);
            }
        }catch(Exception e){
            logger.error("计算点赞积分出错，",e);
        }
    }
    private void buildScoreVoByUnPraise(UserScoreVo userScoreVo){
        try{
            String num = redisCache.get(Constant.REDIS.SCORE_NUM_UNPRAISE+userScoreVo.getUserCode());
            int pnum = 0;
            if (num!=null){
                pnum = Integer.valueOf(num);
            }
            if(pnum==9){
                userScoreVo.setScore(-2);
                userScoreDao.addUserScore(userScoreVo);
                redisCache.set(Constant.REDIS.SCORE_NUM_UNPRAISE+userScoreVo.getUserCode(),0);
            }else{
                redisCache.set(Constant.REDIS.SCORE_NUM_UNPRAISE+userScoreVo.getUserCode(),pnum+1);
            }
        }catch(Exception e){
            logger.error("计算取消点赞积分出错，",e);
        }
    }

    private void buildScoreVoByReply(UserScoreVo userScoreVo){
        try{
            String num = redisCache.get(Constant.REDIS.SCORE_NUM_REPLY+userScoreVo.getUserCode());
            int pnum = 0;
            if (num!=null){
                pnum = Integer.valueOf(num);
            }
            if(pnum==9){
                userScoreVo.setScore(2);
                userScoreDao.addUserScore(userScoreVo);
                redisCache.set(Constant.REDIS.SCORE_NUM_REPLY+userScoreVo.getUserCode(),0);
            }else{
                redisCache.set(Constant.REDIS.SCORE_NUM_REPLY+userScoreVo.getUserCode(),pnum+1);
            }
        }catch(Exception e){
            logger.error("计算取消点赞积分出错，",e);
        }
    }

    private void buildScoreVoByLogin(UserScoreVo userScoreVo){
        try{
            //获取用户当天登录次数
            String num = redisCache.get(Constant.REDIS.LOGIN_PERDAY_NUM+userScoreVo.getUserCode());
            if (num==null){
                userScoreVo.setScore(2);
                userScoreDao.addUserScore(userScoreVo);
                //设置缓存
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY)*60*60; // 时
                int minutes = calendar.get(Calendar.MINUTE)*60;    // 分
                int seconds = calendar.get(Calendar.SECOND);    // 秒
                redisCache.set(Constant.REDIS.LOGIN_PERDAY_NUM+userScoreVo.getUserCode(), String.valueOf(1),86400-hours-minutes-seconds);
            }
        }catch(Exception e){
            logger.error("计算点赞积分出错，",e);
        }
    }

}
