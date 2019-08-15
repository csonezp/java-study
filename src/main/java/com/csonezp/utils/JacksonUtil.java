package com.csonezp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by csonezp on 2019/7/7.
 */
@Log
public class JacksonUtil {
    private static volatile ObjectMapper mapper;
    public static ObjectMapper getMapperInstance(boolean createNew) {
        if (createNew) {
            return new ObjectMapper();
        } else if (mapper == null) {
            synchronized (JacksonUtil.class) {
                if (mapper == null) {
                    mapper = new ObjectMapper();
                    mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    mapper.setLocale(Locale.CHINA);
                    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
            }
        }
        return mapper;
    }

    /**
     * 获取ObjectMapper单例，默认配置如下：
     * <br>mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
     * <br>mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     * <br>mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
     * <br>mapper.setLocale(Locale.CHINA);
     * <br>mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
     *
     * @return mapper单例
     */
    public static ObjectMapper getMapperInstance() {
        return getMapperInstance(false);
    }

    /**
     * 将java对象转换为字符串
     *
     * @param param java 对象
     * @return json字符串
     */
    public static String toJson(Object param) {
        if (param == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            String dataJson = objectMapper.writeValueAsString(param);
            return dataJson;
        } catch (Exception e) {
//            LOGGER.warn("toJson", e);
        }
        return null;
    }

    /**
     * 将java对象为字符串 类名作为json的顶级属性
     *
     * @param param java 对象
     * @returnjson字符串
     */
    public static String toJsonWithRoot(Object param) {
        if (param == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
            String dataJson = objectMapper.writeValueAsString(param);
            return dataJson;
        } catch (Exception e) {
//            LOGGER.warn("toJson", e);
        }
        return null;
    }

    /**
     * 将json字符串转换为java对象  ，解析字符串时，将类的名字或者别名作为顶级属性来解析
     *
     * @param json json字符串
     * @param cls  java对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToBeanWithRoot(String json, Class<T> cls) {
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
//            LOGGER.warn("jsonToBean", e);
            return null;
        }
    }

    /**
     * 将json字符串转换为java对象
     *
     * @param json json字符串
     * @param cls  java对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> cls) {
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
//            LOGGER.warn("jsonToBean", e);
            return null;
        }
    }

    public static <T> T jsonToBean(String json, Class<T> cls, T defaultBean) {
        if (StringUtils.isEmpty(json)) {
            return defaultBean;
        }
        return jsonToBean(json, cls);
    }

    /**
     * 将json字符串转换为java对象
     *
     * @param json          json字符串
     * @param typeReference 复杂的java对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToBeanByTypeReference(String json, TypeReference typeReference) {
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
//            log.warning("jsonToBean", e);
            return null;
        }
    }

    /**
     * 将json字符串转换为java对象   ，解析字符串时，将类的名字或者别名作为顶级属性来解析
     *
     * @param json          json字符串
     * @param typeReference 复杂的java对象类型
     * @param <T>
     * @return
     */
    public static <T> T jsonToBeanByTypeReferenceWithRoot(String json, TypeReference typeReference) {
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapperInstance(false);
            objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
//            LOGGER.warn("jsonToBean", e);
            return null;
        }
    }
}
