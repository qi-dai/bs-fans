package com.eden.fans.bs.domain.response;

import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.user.AttentionVo;

import java.util.List;

/**
 * Created by Administrator on 2016/4/2.
 */
public class AttentionResponse {
    private List<AttentionVo> attentionVoList;
    private Page page;

    public List<AttentionVo> getAttentionVoList() {
        return attentionVoList;
    }

    public void setAttentionVoList(List<AttentionVo> attentionVoList) {
        this.attentionVoList = attentionVoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
