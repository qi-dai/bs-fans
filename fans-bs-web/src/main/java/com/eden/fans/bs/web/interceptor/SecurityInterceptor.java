package com.eden.fans.bs.web.interceptor;

import com.eden.fans.bs.common.util.Constant;
import com.eden.fans.bs.dao.util.RedisCache;
import com.eden.fans.bs.domain.response.SystemErrorEnum;
import com.eden.fans.bs.domain.response.UserErrorCodeEnum;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by lirong on 2016/3/3.
 * 拦截需要登录的所有请求
 * 拦截前需在spring-mvc.xml配置拦截url
 */
public class SecurityInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);
    @Autowired
    private RedisCache redisCache;

    private static Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setContentType("text/html;charset=utf-8");
        //让浏览器用utf8来解析返回的数据
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        //servlet用UTF-8转码，而不是用默认的ISO8859
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter pwriter = null;
        try{
            String token = httpServletRequest.getParameter("token");
            String phone = httpServletRequest.getParameter("phone");
            logger.error("权限认证开始-sid:{},phone:{}",token,phone);
            String requestUri = httpServletRequest.getRequestURI();
            logger.error("请求路径：{}",requestUri);
            /**
             * 获取该手机号已登录tokens集合
             * 格式：ja7e82sdqw212d_ssdki2279kklas_7iosl1aFCMQ1as
             * 如果传入token在登录tokens中，则合法用户，否则非法请求，拒绝服务
             * */
            String tokenSrc = redisCache.get(Constant.REDIS.TOKEN+phone);
            if(tokenSrc!=null){
                String[] tokenArr = tokenSrc.split("_");
                for (String tStr : tokenArr){
                    if(tStr.equals(token)) return true;
                }
            }
            pwriter = httpServletResponse.getWriter();
            ServiceResponse<String> interCeptorResponse = new ServiceResponse<String>(SystemErrorEnum.ILLEGAL_REQUEST);
            pwriter.print(gson.toJson(interCeptorResponse));
            pwriter.flush();
        }catch (Exception e){
            logger.error("安全校验拦截器出错，默认通过~",e);
            return true;
        }finally {
            if(pwriter!=null)
                pwriter.close();
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
