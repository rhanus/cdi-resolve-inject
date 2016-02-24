package com.github.rhanus.cdi.injectresolver;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER})
@Qualifier
public @interface ResolveFrom {
    @Nonbinding
    String systemProperty();
}