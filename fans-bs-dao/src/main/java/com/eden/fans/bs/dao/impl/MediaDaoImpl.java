package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IMediaDao;
import com.eden.fans.bs.domain.user.UserMediaVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/23.
 */
@Repository
public class MediaDaoImpl extends BaseDao<UserMediaVo> implements IMediaDao {
    @Override
    public boolean addUserMediaVo(UserMediaVo userMediaVo){
        return super.insert("UserMediaMapper.addUserMediaVo",userMediaVo);
    }

    @Override
    public int countUserMediaNumByType(Long userCode, String mediaType) {
        UserMediaVo userMediaVo = new UserMediaVo();
        userMediaVo.setUserCode(userCode);
        userMediaVo.setUmType(mediaType);
        return super.queryCountForObject("UserMediaMapper.countUserMediaByType",userMediaVo);
    }

    @Override
    public List<UserMediaVo> qryUserMediaByPage(Map<String, Object> params) {
        return super.queryForList("UserMediaMapper.qryUserMediaVos",params);
    }

    @Override
    public boolean updateMediaVo(UserMediaVo userMediaVo) {
        return super.update("UserMediaMapper.updateUserMedia",userMediaVo);
    }
}
