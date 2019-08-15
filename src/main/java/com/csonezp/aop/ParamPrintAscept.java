package com.csonezp.aop;

import com.csonezp.utils.JacksonUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by csonezp on 2019/7/7.
 */
@Aspect
@Component
@Slf4j
public class ParamPrintAscept {
    private static final Pattern pattern = Pattern.compile("execution\\([\\w\\$]+\\s+([\\w\\$]+\\.)+([\\w\\$]+)\\([\\w\\$\\,]*\\)\\)");


    /**
     * 打印方法开始时的输入参数序列
     *
     * @param pjp       连接点对象
     * @param logPrefix 日志方法前缀（默认是类名.方法名）
     * @param set       排除的对象的索引set
     */
    private void printStartLog(ProceedingJoinPoint pjp, String logPrefix, Set<String> set, boolean requestOnly) {
        try {
            Object[] params = pjp.getArgs();
            if (params == null || params.length <= 0) {
                log.info(logPrefix + "-start");
                return;
            }
            StringBuilder strOther = new StringBuilder();
            StringBuilder strRequest = new StringBuilder();
            for (int i = 0; i < params.length; i++) {
                //如果是排除的参数，跳过打印
                if (set != null && !set.isEmpty() && set.contains(String.valueOf(i))) {
                    continue;
                }
                Object param = params[i];
                if (param != null) {
                    if (param instanceof ServletRequest) {
                        strRequest.append(getRequestParam(set, (ServletRequest) param));
                    }
                    if (!requestOnly && !(param instanceof ServletRequest)) {
                        strOther.append(JacksonUtil.toJson(param) + " ");
                    }
                }
            }


            String logInfo = "Request-" + logPrefix + "-input--";
            if (requestOnly) {
                logInfo += "requestParam: " + strRequest.toString();
            } else {
                logInfo += "funcParam: " + strOther;
            }
            log.info(logInfo);
        } catch (Exception e) {
            log.info("no log");
        }
    }

    private String getRequestParam(Set<String> set, ServletRequest param) {
        Map<String, String[]> parameterMap = param.getParameterMap();
        Map<String, String[]> res = new HashMap<String, String[]>();
        for (Map.Entry<String, String[]> parameter : parameterMap.entrySet()) {
            if (set != null && !set.isEmpty() && set.contains(parameter.getKey())) {
                continue;
            }
            res.put(parameter.getKey(), parameter.getValue());
        }
        return JacksonUtil.toJson(res);
    }

    /**
     * 打印方法结束时的输出参数序列
     *
     * @param business  方法输出参数对象
     * @param logPrefix 日志方法前缀（默认是类名.方法名）
     */
    private void printEndLog(Object business, String logPrefix) {

        //打印日志结果的内容
        if (business != null) {
            log.info("Request-" + logPrefix + "-output--:" + JacksonUtil.toJson(business));
        } else {
            log.info("Request-" + logPrefix + "-output");
        }
    }

    /**
     * 执行所有配置logPrintAnnotation标签的日志的切面
     *
     * @param pjp                连接点
     * @param logPrintAnnotation 注解对象
     * @return 方法返回对象
     * @throws Throwable
     */
    @Around(value = "(execution(* *(..)) && @annotation(logPrintAnnotation))", argNames = "pjp,logPrintAnnotation")
    public Object printLog(final ProceedingJoinPoint pjp, ParamPrint logPrintAnnotation) throws Throwable {
        Object business = null;
        String logPrefix = getPrintPrefixLog(pjp);
        try {
            //创建配置的排除参数序列
            Set<String> set = new HashSet<String>();
            if (logPrintAnnotation.exceptParamIndex() != null && logPrintAnnotation.exceptParamIndex().length > 0) {
                for (String index : logPrintAnnotation.exceptParamIndex()) {
                    set.add(index);
                }
            }
            printStartLog(pjp, logPrefix, set, logPrintAnnotation.requestOnly());
            //执行方法
            business = pjp.proceed();
        } finally {
            //Todo:如果需要打印返回参数，开启这里
            printEndLog(business, logPrefix);
        }
        return business;
    }

    /**
     * 链接点pjp.toString() 简化日志打印前缀 类名.方法名
     *
     * @param pjp 链接点
     * @return 简化后的日志打印前缀
     */
    private String getPrintPrefixLog(ProceedingJoinPoint pjp) {
        String resStr = pjp.toString();
        StringBuilder patternStr = new StringBuilder();
        //正则匹配相关的字符串
        Matcher matcher = pattern.matcher(resStr);
        if (matcher.find()) {
            int count = matcher.groupCount();
            //拼装组装好的字符串
            for (int i = 1; i <= count; i++) {
                patternStr.append(matcher.group(i));
            }
        }
        patternStr.append(".");
        return patternStr.toString();
    }
}