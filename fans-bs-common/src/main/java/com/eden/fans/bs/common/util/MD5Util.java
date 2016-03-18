package com.eden.fans.bs.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/3/18.
 */
public class MD5Util {

    public static String md5s(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)i += 256;
                if (i < 16)buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String text, String salt) throws Exception {
        byte[] bytes = (text + salt).getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return sb.toString();
    }

    public static void main(String agrs[]) {
        MD5Util md51 = new MD5Util();

        try {
            String x = md51.md5("073@1dfg", "k9opz");
            String y = md51.md5s("073@1dfgk9opz");//加密4
            System.out.println(x);
            System.out.println(y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
