package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IDictDao;
import com.eden.fans.bs.domain.dict.DictVo;
import com.eden.fans.bs.domain.request.DictRequest;
import com.eden.fans.bs.domain.response.DictErrorEnum;
import com.eden.fans.bs.domain.response.DictPageResponse;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.response.SystemErrorEnum;
import com.eden.fans.bs.service.IDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/29.
 */
@Service
public class DictServiceImpl implements IDictService {
    private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

    @Autowired
    private IDictDao dictDao;

    @Override
    public ServiceResponse<Boolean> addDictVo(DictVo dictVo) {
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            serviceResponse = new ServiceResponse<Boolean>(dictDao.addDictVo(dictVo));
        }catch(Exception e){
            logger.error("查询数据字典错误，",e);
            serviceResponse = new ServiceResponse<Boolean>(SystemErrorEnum.FAILED);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<List<DictVo>> queryDictVos(DictRequest dictRequest) {
        ServiceResponse<List<DictVo>> serviceResponse = null;
        try{
            DictVo dictVo = new DictVo();
            dictVo.setDcode(dictRequest.getCode());
            serviceResponse = new ServiceResponse<List<DictVo>>(dictDao.queryDictVos(dictVo));
        }catch(Exception e){
            logger.error("查询数据字典错误，",e);
            serviceResponse = new ServiceResponse<List<DictVo>>(DictErrorEnum.QRY_MEDIA_ERROR);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> updateDictVo(DictVo dictVo) {
        return null;
    }

    @Override
    public ServiceResponse<DictPageResponse> queryDictVosByPage(Map<String, Object> params) {
        return null;
    }
}
