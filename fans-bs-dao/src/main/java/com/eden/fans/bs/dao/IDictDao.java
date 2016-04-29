package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.dict.DictVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/29.
 */
public interface IDictDao {
    public boolean addDictVo(DictVo dictVo);
    public List<DictVo> queryDictVos(DictVo dictVo);
    public boolean updateDictVo(DictVo dictVo);
    public List<DictVo> queryDictVosByPage(Map<String,Object> params);
}
