package com.eden.fans.bs.web.aop;

import com.eden.fans.bs.domain.request.*;
import org.springframework.stereotype.Component;

/**
 * Created by lirong5 on 2016/3/24.
 * 统一注册需要使用参数校验的
 */
@Component
public class ReqParamRegister {
    static {
        BaseEntity.init(LoginRequest.class);
        BaseEntity.init(RegisterRequest.class);
        BaseEntity.init(StatusUpdateRequest.class);
        BaseEntity.init(SetAdminRequest.class);
        BaseEntity.init(ResetPwdRequest.class);
        BaseEntity.init(AttentionRequest.class);
        BaseEntity.init(QryFromAttRequest.class);
        BaseEntity.init(QryToAttRequest.class);
        BaseEntity.init(MediaRequest.class);
        BaseEntity.init(QryUserMediaVos.class);
        BaseEntity.init(UpdateMediaRequest.class);
        BaseEntity.init(QryUserListRequest.class);
        BaseEntity.init(FootBallScoreAddReq.class);
        BaseEntity.init(PraiseRequest.class);
    }
}
