package xyz.endelith.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

    EventOrder order() default EventOrder.NORMAL;

    boolean ignoreCancelled() default false;
}
