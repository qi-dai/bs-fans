package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.AttentionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lirong5 on 2016/4/1.
 */
public interface IAttentionDao {
    public boolean addAttention(AttentionVo attentionVo);
    public AttentionVo qryAttention(AttentionVo attentionVo);
    public List<AttentionVo> qryAttentions(String sqlId,Map<String,Object> params);
    public boolean updateAttention(AttentionVo attentionVo);
    public int countAttentions(String sqlId,Long qryUserCode);
}
