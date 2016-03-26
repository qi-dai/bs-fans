package com.eden.fans.bs.web.aop;

import com.eden.fans.bs.domain.request.LoginRequest;
import com.eden.fans.bs.domain.request.RegisterRequest;
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
    }
}
