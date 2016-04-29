package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IDictDao;
import com.eden.fans.bs.domain.dict.DictVo;
import com.eden.fans.bs.domain.user.AttentionVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/29.
 */
@Repository
public class DictDaoImpl  extends BaseDao<DictVo> implements IDictDao{


    @Override
    public boolean addDictVo(DictVo dictVo) {
        return super.insert("DictoryMapper.addDict",dictVo);
    }

    @Override
    public List<DictVo> queryDictVos(DictVo dictVo) {
        return super.queryForList("DictoryMapper.qryDictItem",dictVo);
    }

    @Override
    public boolean updateDictVo(DictVo dictVo) {
        return super.update("DictoryMapper.updateDictVo",dictVo);
    }

    @Override
    public List<DictVo> queryDictVosByPage(Map<String,Object> params) {
        return super.queryForList("DictoryMapper.queryDictVosByPage", params);
    }
}
