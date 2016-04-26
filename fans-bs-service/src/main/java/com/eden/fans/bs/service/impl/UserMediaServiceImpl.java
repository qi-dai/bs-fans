package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IMediaDao;
import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.request.QryUserMediaVos;
import com.eden.fans.bs.domain.request.MediaRequest;
import com.eden.fans.bs.domain.request.UpdateMediaRequest;
import com.eden.fans.bs.domain.response.MediaErrorCodeEnum;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.response.UserMediaResponse;
import com.eden.fans.bs.domain.user.UserMediaVo;
import com.eden.fans.bs.service.IUserMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/24.
 */
@Service
public class UserMediaServiceImpl implements IUserMediaService {
    private static Logger logger = LoggerFactory.getLogger(UserMediaServiceImpl.class);
    @Autowired
    private IMediaDao mediaDao;//用户多媒体Dao
    @Override
    public ServiceResponse<Boolean> addMediaVo(MediaRequest saveMediaRequest) {
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            UserMediaVo userMediaVo = new UserMediaVo();
            userMediaVo.setUserCode(saveMediaRequest.getUserCode());
            userMediaVo.setUmType(saveMediaRequest.getUmType());
            userMediaVo.setUmUrl(saveMediaRequest.getUmUrl());
            mediaDao.addUserMediaVo(userMediaVo);
            serviceResponse = new ServiceResponse<Boolean>(true);
        }catch(Exception e){
            logger.error("插入数据库失败！",e);
            serviceResponse = new ServiceResponse<Boolean>(MediaErrorCodeEnum.ADD_MEDIA_ERROR);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<UserMediaResponse> getUserMediaVos(QryUserMediaVos qryUserMediaVos) {
        ServiceResponse<UserMediaResponse> serviceResponse = null;
        try{
            int totalNumber = mediaDao.countUserMediaNumByType(qryUserMediaVos.getUserCode(),qryUserMediaVos.getUmType());
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("userCode",qryUserMediaVos.getUserCode());
            params.put("umType",qryUserMediaVos.getUmType());
            params.put("isValid",0);
            Page page= new Page(qryUserMediaVos.getPageNumber());
            page.setCurrentPage(qryUserMediaVos.getCurrentPage());
            page.setTotalNumber(totalNumber);//重新计算分页信息
            params.put("page",page);
            UserMediaResponse userMediaResponse = new UserMediaResponse();
            userMediaResponse.setPage(page);
            userMediaResponse.setAttentionVoList(mediaDao.qryUserMediaByPage(params));
            serviceResponse = new ServiceResponse<UserMediaResponse>(userMediaResponse);
        }catch(Exception e){
            logger.error("查询多媒体信息失败，数据库异常！",e);
            serviceResponse = new ServiceResponse(MediaErrorCodeEnum.QRY_MEDIA_FAILED);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> updateUserMediaVo(UpdateMediaRequest updateMediaRequest) {
        ServiceResponse<Boolean> serviceResponse = null;
        try{
            UserMediaVo userMediaVo = new UserMediaVo();
            userMediaVo.setUserCode(updateMediaRequest.getUserCode());
            userMediaVo.setId(updateMediaRequest.getId());
            userMediaVo.setIsValid(updateMediaRequest.getIsValid());
            serviceResponse = new ServiceResponse<Boolean>(mediaDao.updateMediaVo(userMediaVo));
        }catch(Exception e){
            logger.error("更新用户多媒体数据库失败！",e);
            serviceResponse = new ServiceResponse<Boolean>(MediaErrorCodeEnum.UPDATE_MEDIA_FAILED);
        }
        return serviceResponse;
    }
}
