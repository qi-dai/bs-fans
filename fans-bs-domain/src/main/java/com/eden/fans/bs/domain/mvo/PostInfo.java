package com.eden.fans.bs.domain.mvo;

import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.eden.fans.bs.domain.enu.PostType;
import com.eden.fans.bs.domain.svo.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 帖子的模型描述
 * 此模型包含大量的枚举字段，该模型仅描述帖子的结构，并提供帖子具体存储的参考
 *
 * Created by Administrator on 2016/3/14.
 */
public class PostInfo implements Serializable {
    /**
     * 帖子的唯一标识
     */
    private String _id;

    /**
     * 帖子的标题
     */
    private String title;

    /**
     *帖子的类型（该字段需要建索引）
     */
    private PostType type;

    /**
     *帖子的内容
     */
    private String content;

    /**
     *创建帖子的用户标识
     */
    private Long userCode;
    private String userName;

    /**
     *帖子包含的图片列表
     */
    private String imgs;

    /**
     *帖子包含的视频列表
     */
    private String videos;

    /**
     *帖子的包含的音乐列表
     */
    private String musics;

    /**
     *帖子包含的其他媒体列表
     */
    private String others;

    /**
     *帖子的创建时间（该字段需要建索引）
     */
    private Date createDate;

    /**
     *帖子的发布时间（该字段需要建索引）
     */
    private Date publishDate;

    /**
     *帖子的状态
     */
    private PostStatus status;

    /**
     *帖子的级别（该字段需要建索引）
     */
    private PostLevel level;

    /**
     *帖子的管理员操作列表
     */
    private List<Integer> operatorList = new ArrayList<Integer>();

    /**
     * 用户对帖子的两种操作：关注和点赞是相互独立的，并且同一类型操作状态是互斥的
     */
    private List<ConcernUser> concernUsers;//该属性的time字段需要建索引
    private List<PraiseUser> praiseUsers;// 该属性的time字段需要建索引

    /**
     * 回帖信息列表
     * @return
     */

    private List<ReplyPostInfo> replyPostInfos = new ArrayList<ReplyPostInfo>();// 该属性的replyTime字段需要建索引

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getMusics() {
        return musics;
    }

    public void setMusics(String musics) {
        this.musics = musics;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public PostLevel getLevel() {
        return level;
    }

    public void setLevel(PostLevel level) {
        this.level = level;
    }

    public List<Integer> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<Integer> operatorList) {
        this.operatorList = operatorList;
    }

    public List<ConcernUser> getConcernUsers() {
        return concernUsers;
    }

    public void setConcernUsers(List<ConcernUser> concernUsers) {
        this.concernUsers = concernUsers;
    }

    public List<PraiseUser> getPraiseUsers() {
        return praiseUsers;
    }

    public void setPraiseUsers(List<PraiseUser> praiseUsers) {
        this.praiseUsers = praiseUsers;
    }

    public List<ReplyPostInfo> getReplyPostInfos() {
        return replyPostInfos;
    }

    public void setReplyPostInfos(List<ReplyPostInfo> replyPostInfos) {
        this.replyPostInfos = replyPostInfos;
    }
}
