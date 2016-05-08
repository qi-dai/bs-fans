package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.domain.user.UserScoreVo;
import com.eden.fans.bs.service.IUserScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/5/7.
 */
public class UserScoreServiceImpl implements IUserScoreService {
    private static final Logger logger = LoggerFactory.getLogger(UserPostServiceImpl.class);

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
    public boolean addUserScore(Long userCode, int scoreType) {
        try{
            UserScoreVo userScoreVo = null;
            switch(scoreType) {
                case 1:
                    userScoreVo = buildPraiseVo(userCode);
                    break;
                case -1:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 2:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -2:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 3:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -3:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 4:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -4:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 5:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -5:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 6:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 7:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -7:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case 8:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
                case -8:
                    userScoreVo = buildUNPraiseVo(userCode);
                    break;
            }

        }catch (Exception e){
            logger.error("增加用户人气积分出错",e);
            return false;
        }
        return false;
    }

    private static class ScoreValue{
        private static int ONE = 1;
        private static int TOW = 2;
        private static int THREE = 3;
        private static int FOUR = 4;
        private static int FIVE = 5;
        private static int SIX = 6;
    }

    private UserScoreVo buildPraiseVo(Long userCode){
        return null;
    }
    private UserScoreVo buildUNPraiseVo(Long userCode){
        return null;
    }
    /**
     *   * 积分种类及计算规则
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
    public static int  PRAISE = 1;
    public static int  UNPRAISE = -1;
    public static int  REPLY = 2;
    public static int  UNREPLY = -2 ;
    public static int  APPROVE_YES = 3;
    public static int  DEL_APPROVED_POST = -3;
    public static int  BEST_POST = 4;
    public static int  UNBEST_POST = -4 ;
    public static int  TOP_POST = 5 ;
    public static int  UN_TOP_POST = -5;
    public static int  BE_REPLYED = 6;
    public static int  ATTENTION = 7;
    public static int  UN_ATTENTION = -7;
    public static int  TO_ATTENTION = 8;
    public static int  UN_TO_ATTENTION = -8;
}
