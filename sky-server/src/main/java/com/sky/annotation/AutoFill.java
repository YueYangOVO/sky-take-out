package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author YueYang
 * Created on 2025/9/15 10:45
 * @version 1.0
 * 自定义注解用来 自动填充公共字段的内容。
 * 比如数据库表中的 create_time update_time; create_user update_user;
 * 用在方法上，标识此方法需要进行字段的自动填充处理
 * Target注解用来标识该注解用在哪里，这里用在方法上。
 * Retention标识此注解是一个运行时注解。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //使用枚举来获取数据库操作类型：update insert
    //value方法的返回值是一个枚举类型
    OperationType value();
}
