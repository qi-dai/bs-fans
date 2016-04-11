package mongo;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.google.gson.Gson;
import junit.framework.TestCase;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;

/**
 * Created by IntelliJ IDEA.
 * Project:BS-FANS
 * User: ShengYanPeng
 * Date: 2016/3/28
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit38ClassRunner.class)
public class Test extends TestCase{
    private static String appKey = "0c2cc885752aba5ead5e6e27";
    private static String masterSecret = "153120b1eba6066ea8f12fa1";
    private static Gson gson = new Gson();
    @org.junit.Test
    public void testJpush(){
        JPushClient jpush = new JPushClient(masterSecret, appKey);
        PushPayload payload =  buildPushObject();
        System.out.println("payload:" + gson.toJson(payload));
        try {
            PushResult result = jpush.sendPush(payload);
            System.out.println("pushResult:" + gson.toJson(result));
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    private PushPayload buildPushObject(){
        return  PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag("admin"))//标识为管理员时进行信息推送
                .setNotification(Notification.alert("eden@push"))
                .setMessage(Message.content("有新贴子需要您审批"))
                .build();
    }
}
