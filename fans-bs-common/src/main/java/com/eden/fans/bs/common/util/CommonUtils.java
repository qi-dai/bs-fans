package com.eden.fans.bs.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/29.
 */
public class CommonUtils {
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public static void main(String[] args){
        String x = "12300001111";
        System.out.println(isMobile(x));
        String x2 = "13300001111";
        System.out.println(isMobile(x2));
        String x3 = "14300001111";
        System.out.println(isMobile(x3));
        String x4 = "15300001111";
        System.out.println(isMobile(x4));
        String x5 = "16300001111";
        System.out.println(isMobile(x5));
        String x7 = "17300001111";
        System.out.println(isMobile(x7));
    }
}
