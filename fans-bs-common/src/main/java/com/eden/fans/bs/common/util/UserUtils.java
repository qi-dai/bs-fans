package com.eden.fans.bs.common.util;

/**
 * Created by Administrator on 2016/3/28.
 */
public class UserUtils {

    public static int getUserStatus(String operType){
        if("01".equals(operType)){//冻结
            return 2;
        }else if("03".equals(operType)){ //拉黑
            return 3;
        }
        return 1;// 02 解冻，04 解除黑名单
    }
}
