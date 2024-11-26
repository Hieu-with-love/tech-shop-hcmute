package com.hcmute.tech_shop.utils;

import java.util.Map;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtil {
    public static <T> T getObject(Map<String, Object> params, String key, Class<T> tClass) {
        Object obj = params.getOrDefault(key, null);

        if (obj != null) {
            if (tClass.getTypeName().equals("java.lang.Long")) {
                obj = obj != "" ? Long.valueOf(obj.toString()) : null;
            } else if (tClass.getTypeName().equals("java.lang.Integer")) {
                obj = obj != "" ? Integer.valueOf(obj.toString()) : null;
            } else if (tClass.getTypeName().equals("java.lang.String")) {
                obj = obj.toString();
            } else if (tClass.getTypeName().equals("java.util.List")) {
                if (obj instanceof List<?>) {
                    return tClass.cast(obj);
                } else if (obj instanceof String) {
                    obj = List.of(((String) obj).split(","))
                            .stream()
                            .map(String::trim)
                            .collect(Collectors.toList());
                }
            }
            return tClass.cast(obj);
        }
        return null;
    }
}
