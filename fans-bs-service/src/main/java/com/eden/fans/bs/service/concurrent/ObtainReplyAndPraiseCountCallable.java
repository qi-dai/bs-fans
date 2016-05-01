package com.eden.fans.bs.service.concurrent;

import com.eden.fans.bs.dao.IPostDao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/4/24
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class ObtainReplyAndPraiseCountCallable implements Callable<Map<String,Map<String,String>>>{
    private String postId;
    private String appCode;
    private IPostDao postDao;
    @Override
    public Map<String,Map<String,String>> call() throws Exception {
        Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String, String>>(1);
        Map<String,String> countMap = new HashMap<String, String>(2);

        Map<String,Object> countInfo = postDao.postCountInfo(appCode, postId);

        Object replyCount = countInfo.get("replyCount");
        Object praiseCount = countInfo.get("praiseCount");

        countMap.put("replyCount",null == replyCount?"0":replyCount+"");
        countMap.put("praiseCount",null == praiseCount?"0":praiseCount+"");

        resultMap.put(postId,countMap);
        return resultMap;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setPostDao(IPostDao postDao) {
        this.postDao = postDao;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
