package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IAttentionDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.request.AttentionRequest;
import com.eden.fans.bs.domain.request.QryFromAttRequest;
import com.eden.fans.bs.domain.request.QryToAttRequest;
import com.eden.fans.bs.domain.response.AttentionErrorCodeEnum;
import com.eden.fans.bs.domain.response.AttentionResponse;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.AttentionVo;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.service.IUserAttentionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lirong5 on 2016/4/1.
 */
@Service
public class UserAttentionServiceImpl implements IUserAttentionService{
    private static Logger logger = LoggerFactory.getLogger(UserAttentionServiceImpl.class);

    @Autowired
    private IAttentionDao attentionDao;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IUserDao userDao;//用户信息
    /**
     * 设置/取消关注
     * */
    public ServiceResponse<Boolean> setAttention(AttentionRequest attentionRequest){
        ServiceResponse<Boolean> setAttentionResponse = null;
        try{
            //1.查询当前登录用户
            UserVo userInfo= redisCache.get(attentionRequest.getPhone(),UserVo.class);
            if(userInfo==null){
                userInfo = userDao.qryUserVoByPhone(attentionRequest.getPhone());
            }
            //2.校验被关注或取消关注用户是否存在
            UserVo tempUser = userDao.qryUserVoByUserCode(attentionRequest.getToUserCode());
            if (tempUser==null){
                setAttentionResponse = new ServiceResponse<Boolean>(AttentionErrorCodeEnum.UPDATE_ATTENTION_FAILED);
                setAttentionResponse.setDetail("被关注/取消关注的用户不存在");
                return setAttentionResponse;
            }
            //3.先查询是否已有关注记录，如果没有直接新增，如果有则更新。
            AttentionVo attentionVo = new AttentionVo();
            attentionVo.setAttType(attentionRequest.getAttentionFlag()?"0":"1");
            attentionVo.setFromUserCode(userInfo.getUserCode());
            attentionVo.setToUserCode(attentionRequest.getToUserCode());
            AttentionVo qryAttentionVo = attentionDao.qryAttention(attentionVo);
            if(qryAttentionVo!=null){
                //执行更新
                boolean updateFlag = attentionDao.updateAttention(attentionVo);
                if(!updateFlag){
                    setAttentionResponse = new ServiceResponse<Boolean>(AttentionErrorCodeEnum.UPDATE_ATTENTION_FAILED);
                    return setAttentionResponse;
                }
                setAttentionResponse = new ServiceResponse<Boolean>(true);
                setAttentionResponse.setDetail("关注/取消关注成功!");
                return setAttentionResponse;
            }else{
                if(attentionRequest.getAttentionFlag()){
                    //4.新增关注记录,无异常绝对插入成功.
                    attentionDao.addAttention(attentionVo);
                }else{
                    //无关注记录，此时发生取消关注，什么都不需要做
                }
                setAttentionResponse = new ServiceResponse<Boolean>(true);
                return setAttentionResponse;
            }
        }catch (Exception e){
            logger.error("设置关注出错,{}，操作数据库异常",attentionRequest,e);
            if(attentionRequest.getAttentionFlag()){//设置关注
                setAttentionResponse = new ServiceResponse<Boolean>(AttentionErrorCodeEnum.UPDATE_ATTENTION_ERROR);
            }else{
                setAttentionResponse = new ServiceResponse<Boolean>(AttentionErrorCodeEnum.UPDATE_ATTENTION_FAILED);
            }
        }
        return setAttentionResponse;
    }

    @Override
    public ServiceResponse<AttentionResponse> getFromAttentions(QryFromAttRequest qryFromAttRequest) {
        ServiceResponse<AttentionResponse> qryFromAttResponse = null;
        try{
            int totalNumber = attentionDao.countAttentions("AttentionMapper.countFromAtt", qryFromAttRequest.getFromUserCode());
            if (totalNumber==0){
                qryFromAttResponse = new ServiceResponse<AttentionResponse>();
                return qryFromAttResponse;
            }
            Page page= new Page(qryFromAttRequest.getPageNumber());
            page.setCurrentPage(qryFromAttRequest.getCurrentPage());
            page.setTotalNumber(totalNumber);//重新计算分页信息
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("fromUserCode", qryFromAttRequest.getFromUserCode());
            List<AttentionVo> results = attentionDao.qryAttentions("AttentionMapper.qryFromAttentionVos", params);
            qryFromAttResponse = new ServiceResponse<AttentionResponse>();
            AttentionResponse attentionResponse = new AttentionResponse();
            attentionResponse.setAttentionVoList(results);
            attentionResponse.setPage(page);
            qryFromAttResponse.setResult(attentionResponse);
        }catch (Exception e){
            logger.error("查询关注好友列表出错,{}，操作数据库异常",qryFromAttRequest,e);
            qryFromAttResponse = new ServiceResponse<AttentionResponse>(AttentionErrorCodeEnum.QRYFROM_ATTENTION_FAILED);
        }
        return qryFromAttResponse;
    }

    @Override
    public ServiceResponse<AttentionResponse> getToAttentions(QryToAttRequest qryToAttRequest) {
        ServiceResponse<AttentionResponse> qryToAttResponse = null;
        try{
            int totalNumber = attentionDao.countAttentions("AttentionMapper.countToAtt", qryToAttRequest.getToUserCode());
            if(totalNumber==0){
                qryToAttResponse = new ServiceResponse<AttentionResponse>();
                return qryToAttResponse;
            }
            Page page= new Page(qryToAttRequest.getPageNumber());
            page.setTotalNumber(totalNumber);
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("page", page);
            params.put("toUserCode",qryToAttRequest.getToUserCode());
            List<AttentionVo> results = attentionDao.qryAttentions("AttentionMapper.qryToAttentionVos", params);
            qryToAttResponse = new ServiceResponse<AttentionResponse>();
            AttentionResponse attentionResponse = new AttentionResponse();
            attentionResponse.setAttentionVoList(results);
            attentionResponse.setPage(page);
            qryToAttResponse.setResult(attentionResponse);
        }catch (Exception e){
            logger.error("查询关注好友列表出错,{}，操作数据库异常",qryToAttRequest,e);
            qryToAttResponse = new ServiceResponse<AttentionResponse>(AttentionErrorCodeEnum.QRYTO_ATTENTION_FAILED);
        }
        return qryToAttResponse;
    }
}
