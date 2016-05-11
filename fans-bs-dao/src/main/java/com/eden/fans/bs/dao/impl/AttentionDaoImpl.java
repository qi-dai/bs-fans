package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IAttentionDao;
import com.eden.fans.bs.domain.user.AttentionVo;
import com.eden.fans.bs.domain.user.UserVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lirong5 on 2016/4/1.
 */
@Repository
public class AttentionDaoImpl extends BaseDao<AttentionVo> implements IAttentionDao {

    @Override
    public boolean addAttention(AttentionVo attentionVo) {
        return super.insert("AttentionMapper.addAttention",attentionVo);
    }

    @Override
    public AttentionVo qryAttention(AttentionVo attentionVo) {
        return super.queryForObject("AttentionMapper.qryAttentionVo",attentionVo);
    }

    @Override
    public List<AttentionVo> qryAttentions(String sqlId,Map<String,Object> params) {
        return super.queryForList(sqlId,params);
    }

    public int countAttentions(String sqlId,Long userCode){
        return super.queryCountForObject(sqlId,userCode);
    }

    @Override
    public int jadgeUserRelation(AttentionVo attentionVo) {
        return super.queryCountForObject("AttentionMapper.jadgeUserRelation",attentionVo);
    }

    @Override
    public boolean updateAttention(AttentionVo attentionVo) {
        return super.update("AttentionMapper.updateAttention",attentionVo);
    }
}
