package com.leopard.universa.utils;

import java.lang.reflect.Field;


public class BeanUtil {

    /**
     * 设置新曾属性
     *
     * @param bean  class-bean
     * @param field 新增字段
     * @param value 值
     * @param <T>
     * @return
     */
    public static <T> T setProperty(T bean, String field, Object value) {
        try {
            if (null != bean) {
                Field f = bean.getClass().getDeclaredField(field);
                return setProperty(bean, f, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 设置已有属性值
     *
     * @param bean  class-bean
     * @param field 已有字段
     * @param value 值
     * @param <T>
     * @return
     */
    public static <T> T setProperty(T bean, Field field, Object value) {
        try {
            if (null != bean) {
                field.setAccessible(true);
                field.set(bean, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
