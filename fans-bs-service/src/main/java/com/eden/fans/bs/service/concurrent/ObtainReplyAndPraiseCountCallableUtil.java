package com.eden.fans.bs.service.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User:ShengYanPeng
 * Date: 2016/4/24
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */

/**
 * 获取帖子回帖数和点赞数
 * 异常信息尚待处理
 */
public class ObtainReplyAndPraiseCountCallableUtil {
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+3);
    public static List<Future<Map<String,Map<String,String>>>> ObtainReplyAndPraiseCount(List<ObtainReplyAndPraiseCountCallable> callables){
        List<Future<Map<String,Map<String,String>>>> futureList = null;
        try {
            futureList = executor.invokeAll(callables);
        } catch (InterruptedException e) {
            // TODO
        }
        return futureList;
    }
}
