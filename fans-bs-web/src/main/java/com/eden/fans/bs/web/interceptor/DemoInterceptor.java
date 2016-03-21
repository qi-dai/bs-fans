package com.eden.fans.bs.web.interceptor;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shengyanpeng on 2016/3/3.
 */
public class DemoInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    private static Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //让浏览器用utf8来解析返回的数据
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        //servlet用UTF-8转码，而不是用默认的ISO8859
        httpServletResponse.setCharacterEncoding("UTF-8");
        String token = httpServletRequest.getParameter("sid");
        String phone = httpServletRequest.getParameter("phone");
        logger.error("权限认证开始-sid:{},phone:{}",token,phone);
        OutputStream ps =httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("");
        Map map=new HashMap();
        map.put("success", false);
        map.put("msg", "拦截器返回非法请求");
        ps.write(gson.toJson(map).getBytes("UTF-8"));
        ps.flush();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
