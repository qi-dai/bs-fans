package com.eden.fans.bs.web.aop;

import com.eden.fans.bs.domain.annotation.ReqCheckParam;
import com.eden.fans.bs.domain.response.ResponseCode;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.response.SystemErrorEnum;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/19.
 */
@Component
@Aspect
public class ControllerAop {
    private static Logger logger = LoggerFactory.getLogger(ControllerAop.class);

    private static Gson gson = new Gson();

    @SuppressWarnings("unchecked")
    protected static final ServiceResponse<Void> requestSuccess = ServiceResponse.successResponse();


    // 配置切入点
    @Pointcut(value = "execution(public  java.lang.String com.eden.fans.bs.web.controller..*.*(..))")
    public void aspect() {
    }

    /**
     * controller处理：
     * 1.打印返回报文全量信息
     * 2.监控接口响应时间，讨论是否要做。
     * 3.其他。
     * */
    @Around("aspect()")
    public Object call(JoinPoint joinPoint) throws Throwable {
        ProceedingJoinPoint pjp = ((ProceedingJoinPoint) joinPoint);
        String executeMethod = pjp.getTarget().getClass().getPackage().getName() + "." + StringUtils.substringBefore(pjp.getSignature().toShortString(), "(");
        /**Done 1.参数统一校验*/
        Annotation[][] z = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            for (Annotation a : z[i]) {
                if (a.annotationType() == ReqCheckParam.class) {
                    if (checkParamValue(joinPoint.getArgs()[i])) {
                        logger.error("参数异常：{}", gson.toJson(joinPoint.getArgs()));
                        return buildParamCheckResponse(SystemErrorEnum.PARAM_NULL, ((ReqCheckParam) a).value());
                    }
                    ServiceResponse<Void> checkResult = verifyNotNullParams(joinPoint.getArgs()[i]);
                    if (checkResult != requestSuccess) {
                        logger.error("必填参数校验不通过：入参{}", gson.toJson(joinPoint.getArgs()[i]));
                        return gson.toJson(checkResult);
                    }
                }
            }
        }
        try {
            //Todo 是否需要打印时间慢的接口。
            Object objectRtn = pjp.proceed();
            String responseJson = gson.toJson(objectRtn);
            logger.error("执行方法executeMethod：{},返回报文：{}",executeMethod,responseJson);
            return objectRtn;
        } catch (Exception e) {
            //已经是最后面向客户流程，必须处理
            throw new Exception(executeMethod,e);
        }catch(Throwable e){
            //已经是最后面向客户流程，必须处理`
            throw new Exception(executeMethod,e);
        }finally {

        }
    }

    public static ServiceResponse<Void> buildParamCheckResponse(SystemErrorEnum code, Object... params) {
        return new ServiceResponse<Void>(code.getCode(), code.getMsg(), String.format(code.getDetail(), params));
    }

    protected static final ServiceResponse<Void> verifyNotNullParams(Object entity) {
        for (Map.Entry<String, BaseEntity.ActionInputNotNullMethod> entry : BaseEntity.getterNotNullMethods(entity.getClass()).entrySet()) {
            Method getterMethod = entry.getValue().getMethod();
            Object val;
            try {
                val = getterMethod.invoke(entity);
            } catch (Exception e) {
                logger.error("", e);
                throw new RuntimeException(e);
            }
            ResponseCode responseCode = entry.getValue().getCode();
            if (val == null || val instanceof String && StringUtils.isBlank((String) val)) {
                return new ServiceResponse<Void>(SystemErrorEnum.PARAM_NULL.code, SystemErrorEnum.PARAM_NULL.msg, responseCode.getMsg());
            }
        }
        return requestSuccess;
    }

    private boolean checkParamValue(Object param) {
        if (param == null) {
            return true;
        }
        if (param instanceof String) {
            if (StringUtils.isBlank((String) param)) {
                return true;
            }
        }
        if (param instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection) param)) {
                return true;
            }
        }

        if (param instanceof Object[]) {
            if (ArrayUtils.isEmpty((Object[]) param)) {
                return true;
            }
        }
        return false;
    }

}
