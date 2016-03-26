package com.eden.fans.bs.domain.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class RegisterReponse implements Serializable {
    private boolean isSuccess;
    private Long userCode;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }
}
