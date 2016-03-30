package com.eden.fans.bs.domain.svo;

import com.eden.fans.bs.domain.enu.PostConcern;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project:BS-FANS
 * User: ShengYanPeng
 * Date: 2016/3/25
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */

/**
 * 关注的帖子
 */
public class ConcernPost implements Serializable{

    /**
     * 帖子id
     */
    private String postId;

    /**
     *帖子标题
     */
    private String title;

    /**
     *帖子状态（1：关注，2取消关注）
     */
    private PostConcern status;

    /**
     *操作时间
     */
    private Date time;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostConcern getStatus() {
        return status;
    }

    public void setStatus(PostConcern status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
