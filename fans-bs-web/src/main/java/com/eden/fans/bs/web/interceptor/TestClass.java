package com.eden.fans.bs.web.interceptor;

/**
 * Created by Administrator on 2016/3/22.
 */
public class TestClass {
    public static void main(String[] args){
        String src = "43746070f2704d22ab2d1b61f63b02d7";
        String src2 = "_43746070f2704d22ab2d1b61f63b02d7";
        String src3 = "070f2704d22ab2d1b61f63b02d7";

        String x = "43746070f2704d22ab2d1b61f63b02d7";

        System.out.println(src.indexOf(x));
        System.out.println(src2.indexOf(x));
        System.out.println(src3.indexOf(x));
    }
}
