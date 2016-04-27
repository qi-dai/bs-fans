package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.UserCountVo;
import com.eden.fans.bs.domain.user.UserVo;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface IUserCountDao {
    /**
     * 统计用户其他各项数据：1.查询关注用户，2.查询被关注用户,3.贡献人气，4.帖子数量，5.相册照片数量，6.视频数量，7.是否已关注
     * */
    public UserCountVo qryUserCountVo(UserVo userVo);
}
