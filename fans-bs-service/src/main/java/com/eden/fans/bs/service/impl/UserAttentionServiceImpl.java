package com.eden.fans.bs.service.impl;

import com.eden.fans.bs.dao.IAttentionDao;
import com.eden.fans.bs.domain.request.AttentionRequest;
import com.eden.fans.bs.domain.response.AttentionErrorCodeEnum;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.AttentionVo;
import com.eden.fans.bs.service.IUserAttentionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lirong5 on 2016/4/1.
 */
@Service
public class UserAttentionServiceImpl implements IUserAttentionService{
    private static Logger logger = LoggerFactory.getLogger(UserAttentionServiceImpl.class);

    @Autowired
    private IAttentionDao attentionDao;

    /**
     * 设置/取消关注
     * */
    public ServiceResponse<Boolean> setAttention(AttentionRequest attentionRequest){
        ServiceResponse<Boolean> setAttentionResponse = null;
        try{
            //1.先查询是否已有关注记录，如果没有直接新增，如果有则更新。
            AttentionVo attentionVo = new AttentionVo();
            attentionVo.setAttType(attentionRequest.getAttentionFlag()?"0":"1");
            attentionVo.setFromUserCode(attentionRequest.getFromUserCode());
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
                    //2.新增关注记录,无异常绝对插入成功.
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
            }
        }
        return setAttentionResponse;
    }
}
