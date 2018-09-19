package com.javarush.task.task38.task3810;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public @interface Author {
    String value() default "";
    Position position() default Position.OTHER;
}
