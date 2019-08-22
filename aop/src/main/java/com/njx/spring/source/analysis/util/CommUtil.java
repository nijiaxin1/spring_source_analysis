package com.njx.spring.source.analysis.util;

import com.njx.spring.source.analysis.annotation.Entity;

import java.lang.annotation.Annotation;

public class CommUtil {
    public static String parseAnno(Object obj) {
        String value = null;
        Class clazz = obj.getClass();
        //判断是否加有这个注解
        if (clazz.isAnnotationPresent(Entity.class)) {
            //获取注解
            Entity entity = (Entity) clazz.getAnnotation(Entity.class);
            //执行方法
            value = entity.value();
        }
        return value;
    }
}
