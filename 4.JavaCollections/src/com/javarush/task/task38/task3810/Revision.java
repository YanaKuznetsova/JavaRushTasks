package com.javarush.task.task38.task3810;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public @interface Revision {
    int revision();
    Date date();
    Author[] authors() default {};
    String comment() default "";
}
