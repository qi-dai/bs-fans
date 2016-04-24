package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.UserMediaVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/23.
 */
public interface IMediaDao {
    public boolean addUserMediaVo(UserMediaVo userMediaVo);
    public int countUserMediaNumByType(Long userCode,String mediaType);
    public List<UserMediaVo> qryUserMediaByPage(Map<String,Object> params);
    public boolean updateMediaVo(UserMediaVo userMediaVo);
}
