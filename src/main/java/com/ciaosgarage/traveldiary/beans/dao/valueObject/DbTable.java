package com.ciaosgarage.traveldiary.beans.dao.valueObject;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)   // .Type 이 클래스 단위
@Retention(RetentionPolicy.RUNTIME)
public @interface DbTable {
    String tablename() default "";

}
