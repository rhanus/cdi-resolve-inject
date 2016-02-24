package com.github.rhanus.cdi.injectresolver;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.Optional;


@ApplicationScoped
public class GreetingProducer {
    @Inject
    @Any
    private Instance<Greeting> greetings;

    @Produces
    @Dependent
    @ResolveFrom(systemProperty = "")
    public Greeting getGreeting(InjectionPoint injectionPoint) {
        // resolve system property
        String property = injectionPoint.getAnnotated().getAnnotation(ResolveFrom.class).systemProperty();
        // bean class name from a system property
        String className = Optional.ofNullable(System.getProperty(property))
                .orElseThrow(() -> new IllegalArgumentException(String.format("System property \'%s\' doesn't maintain a loadable class", property)));

        // load class by name
        Class beanClass;
        try {
            beanClass = getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(String.format("System property \'%s\' doesn't maintain a loadable class: %s", property, className));
        }

        // get instance by class
        Instance beanInstance = greetings.select(beanClass);
        if (beanInstance.isUnsatisfied()) {
            throw new IllegalArgumentException("No bean for  " + className);
        }
        if (beanInstance.isAmbiguous()) {
            throw new IllegalArgumentException("Multiple beans for " + className);
        }

        return (Greeting) beanInstance.get();
    }
}
