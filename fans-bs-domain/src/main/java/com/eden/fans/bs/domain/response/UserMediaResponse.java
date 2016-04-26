package com.eden.fans.bs.domain.response;

import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.user.AttentionVo;
import com.eden.fans.bs.domain.user.UserMediaVo;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class UserMediaResponse {
    private List<UserMediaVo> attentionVoList;
    private Page page;

    public List<UserMediaVo> getAttentionVoList() {
        return attentionVoList;
    }

    public void setAttentionVoList(List<UserMediaVo> attentionVoList) {
        this.attentionVoList = attentionVoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
