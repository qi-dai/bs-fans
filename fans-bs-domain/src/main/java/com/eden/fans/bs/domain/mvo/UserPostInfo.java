package com.eden.fans.bs.domain.mvo;

/**
 * Created by IntelliJ IDEA.
 * Project:BS-FANS
 * User: ShengYanPeng
 * Date: 2016/3/25
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */

/**
 * 点赞的帖子
 */
import com.eden.fans.bs.domain.svo.ConcernPost;
import com.eden.fans.bs.domain.svo.PraisePost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户维度操作帖子的模型
 */
public class UserPostInfo implements Serializable{
    /**
     * 用户编号
     */
    private Long userCode;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 关注过的帖子列表
     */
    private List<ConcernPost> concerns = new ArrayList<ConcernPost>();

    /**
     * 点赞的帖子列表
     */
    private List<PraisePost> praises = new ArrayList<PraisePost>();

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ConcernPost> getConcerns() {
        return concerns;
    }

    public void setConcerns(List<ConcernPost> concerns) {
        this.concerns = concerns;
    }

    public List<PraisePost> getPraises() {
        return praises;
    }

    public void setPraises(List<PraisePost> praises) {
        this.praises = praises;
    }
}
