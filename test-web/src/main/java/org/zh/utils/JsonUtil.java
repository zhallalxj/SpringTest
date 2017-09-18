package org.zh.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

/**
 * @author ZhaoHang
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("convert json fail: obj is " + obj.toString());
        }
    }

    public static <T> T toJson(String json, Class<T> t) {
        if(StringUtils.isBlank(json)) return null;

        try {
            return mapper.readValue(json, t);
        } catch (Exception e) {
            throw new RuntimeException("convert json fail: json is " + json);
        }
    }
}
