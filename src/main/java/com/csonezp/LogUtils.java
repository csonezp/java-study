package com.csonezp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Created by Spring on 2018/5/14.
 */
public class LogUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    private static Logger payBorderScribeLog = LoggerFactory.getLogger("PayBorderScribeLog");
    private static Logger payInvokeScribeLog = LoggerFactory.getLogger("PayInvokeScribeLog");
    private static Logger payBorderXMDLog = LoggerFactory.getLogger("PayBorderXMDLog");
    private static Logger payInvokeXMDLog = LoggerFactory.getLogger("PayInvokeXMDLog");

    private static Logger payTraceScribeLog = LoggerFactory.getLogger("PayTraceScribeLog");
    private static Logger payTraceXMDLog = LoggerFactory.getLogger("PayTraceXMDLog");

    private static final String ERROR_CODE = "err";
    private static final String STATUS = "status";
    private static final String EXTRA = "extra";


    public static void addTraceLog(Map<String, String> message) {
        try {
            message.put("addtime", Long.toString(System.currentTimeMillis()));
            message.put("trace_id", getTraceId());
//            message.put("hostname", ApplicationUtils.getHostName());

            payTraceScribeLog.info(formatLog(message, true));
            payTraceXMDLog.info(formatLog(message, false));
        } catch (Exception e) {
            payTraceXMDLog.error("[Trace Log] add trace log exception, msg=", e);
        }
    }


    /**
     * 构建边界日志
     * try catch原因，日志打印不能影响主流程
     *
     * @param requestParam 入参
     * @param resultValue  出参
     * @param method       方法名
     */
    public static void addBorderLog(String requestParam,
                                    String resultValue,
                                    String method,
                                    String timeStr,
                                    boolean isSuccess) {

        try {
            String success = isSuccess ? "success" : "fail";
            Map<String, String> valueMap = new HashMap<>(8);
            valueMap.put("trace_id", getTraceId());
            valueMap.put("domain", "thrift");
            valueMap.put("uri", method);
            valueMap.put("in_get", timeStr);
            valueMap.put("in_post", requestParam);
            valueMap.put("out", resultValue);
//            valueMap.put("reserve_hostname", ApplicationUtils.getHostName());
            valueMap.put("_mt_action", success);
//            valueMap.put("_mt_clientip", ClientInfoUtil.getClientIp());

            payBorderScribeLog.info(formatLog(valueMap, true));
            payBorderXMDLog.info("method = {}, {}", method, formatLog(valueMap, false));
        } catch (Exception e) {
            payBorderXMDLog.error("[Border Log] add border log exception, msg=", e);
        }
    }

    /**
     * 外部服务（除收银台外服务）调用边界日志
     * try catch原因，日志打印不能影响主流程
     *
     * @param requestParam 入参
     * @param resultValue  出参
     * @param method       方法名
     */
    public static void addForeignLog(Map<String, Object> requestParam,
                                     String resultValue,
                                     String method,
                                     String protoal,
                                     String timeStr) {
        try {
            Map<String, String> valueMap = new HashMap<>(8);
//            valueMap.put("request", JsonUtils.toJSONString(requestParam));
            valueMap.put("response", resultValue);
            valueMap.put("traceid", getTraceId());
            valueMap.put("attach", protoal);
            valueMap.put("_mt_action", method);
//            valueMap.put("_mt_clientip", ClientInfoUtil.getClientIp());

            payInvokeScribeLog.info(formatLog(valueMap, true));
            String value = formatLog(valueMap, false);
            payInvokeXMDLog.info("method = {}, {}", method, value);
        } catch (Exception e) {
            payBorderXMDLog.error("[Border Log] add border log exception, msg=", e);
        }
    }

    /**
     * 如PHP对应的HIVE表，需要按这种格式
     *
     * @param logMap
     * @return
     */
    private static String formatLog(Map<String, String> logMap, boolean needUrlEncode) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = logMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            String value = entry.getValue();
            try {
                stringBuilder.append(value != null ? (needUrlEncode ? URLEncoder.encode(value, "UTF-8") : value) : "");
            } catch (Exception e) {
                payTraceXMDLog.error("[Trace Log] build string occur exception, msg=", e);
            }
            stringBuilder.append("&");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


    private static String getTraceId() {
        String traceId = "";
        try {
//            traceId = Tracer.id();
        } catch (Exception exp) {
            LOGGER.error("[Mtrace Id] get trace is occur exception, msg=", exp);
            traceId = genTraceIdByUUID();
        }
        return traceId;
    }

    private static String genTraceIdByUUID() {
        CRC32 crc32 = new CRC32();
        crc32.update(UUID.randomUUID().toString().getBytes());
        return Long.toString(Math.abs(crc32.getValue()));
    }
} 