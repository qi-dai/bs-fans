package com.eden.fans.bs.service.concurrent;

import com.eden.fans.bs.domain.user.UserVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/4/24
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */

/**
 * 根据用户code获取用户信息
 * 异常信息尚待处理
 */
public class ObtainUserInfoCallableUtil {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+3);
    public static Map<String,Map<String,String>> ObtainReplyAndPraiseCount(List<ObtainUserInfoCallable> callables){
        Map<String,Map<String,String>> resultMap = new HashMap<String, Map<String, String>>(10);
        try {
            List<Future<Map<String,String>>> futureList = executor.invokeAll(callables);
            for(Future<Map<String,String>> future:futureList){
                try {
                    Map<String,String> map = future.get();
                    resultMap.put(map.get("postId"),map);
                } catch (ExecutionException e) {
                    // TODO
                }
            }
        } catch (InterruptedException e) {
            // TODO
        }
        return resultMap;
    }
}
