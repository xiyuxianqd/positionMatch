package com.xiyuxian.positionmatch.common;

import java.util.Collection;
import java.util.Map;

/**
 * 公共判空工具
 *
 * @author chenhu
 * @date 2021-07-12
 */
public class CommonUtil {
    /**
     * 判断对象是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(Object value) {
        if (null == value) {
            return true;
        } else if ((value instanceof String) && (((String) value).trim().length() < 1)) {
            return true;
        } else if (value.getClass().isArray()) {
            if (0 == java.lang.reflect.Array.getLength(value)) {
                return true;
            }
        } else if (value instanceof Collection) {
            if (((Collection) value).isEmpty()) {
                return true;
            }
        } else if (value instanceof Map) {
            if (((Map) value).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断对象是否不为空
     *
     * @param object
     * @return 不为空
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 判断所有元素是否都为空
     *
     * @param objects
     * @return
     */
    public static boolean allIsEmpty(Object... objects) {
        if (isNotEmpty(objects)) {
            for (Object object : objects) {
                if (isNotEmpty(object)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断所有元素是否都不为空
     *
     * @param objects
     * @return
     */
    public static boolean allIsNotEmpty(Object... objects) {
        if (isNotEmpty(objects)) {
            for (Object object : objects) {
                if (isEmpty(object)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 对象为空时的默认值
     *
     * @param t
     * @param defaultGenerics
     * @param <T>
     * @return
     */
    public static <T> T defaultIfEmpty(T t, T defaultGenerics) {
        return isEmpty(t) ? defaultGenerics : t;
    }

}
