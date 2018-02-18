package com.xmg.p2p.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记需要登录的注解
 * @Author: Squalo
 * @Date: 2018/2/18 - 15:36     day04_05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequireLogin {
}
