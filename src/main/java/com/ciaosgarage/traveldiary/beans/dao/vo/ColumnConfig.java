package com.ciaosgarage.traveldiary.beans.dao.vo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnConfig {
    RwType rwType() default RwType.EDITABLE;

    CryptOption cryptOption() default CryptOption.OFF;

    String columnName() default "";

    int size() default 0;

    ColumnType columnType() default ColumnType.NONE;
}
