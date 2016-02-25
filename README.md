Resolve the injection from a system property maintaining a type information
===========================================================================
## Motivation
Recently I was asked to help on a project migrating from jdk6/spring3/tomcat7 to jdk8/javaee7/wildlfy9
to figure out how to replace a specific Spring feature for which there is no direct replacement in Java EE.

Spring application was defined by a configuration file where a bean of interest used 3 references of the same
type and the configuration file resolved which fields were bounded to which implementations.

```java
interface Greeting {...}

class GreetingBean {
    private Greeting firstGreeting;
    private Greeting secondGreeting;
    private Greeting thirdGreeting;
}
```

```xml
<beans...>
   <bean id="greetingBean" class="apackage.GreetingBean">
        <property name="firstGreeting" ref="firstGreeting"/>
        <property name="secondGreeting" ref="secondGreeting"/>
        <property name="thirdGreeting" ref="thirdGreeting"/>
    </bean>

    <bean id="firstGreeting" class="apackage.FancyGreeting"/>
    <bean id="secondGreeting" class="apackage.SimpleGreeting"/>
    <bean id="thirdGreeting" class="apackage.FormalGreeting"/>
</beans>
```

Such a configuration was no accidental. The bean of interest was a central service logic bean and there were
several project flavours with different configurations of the bean of interest so that there was a good reason
to support existing implementation with minimal or better no changes.

# Migration design
At these days CDI is the natural choice of how to wire components together when migrating to Java EE.
Here the only acceptable code changes were supposed to be as follows:
```java
class GreetingBean {
    @Inject
    private Greeting firstGreeting;
    @Inject
    private Greeting secondGreeting;
    @Inject
    private Greeting thirdGreeting;
}
```
and no custom configuration because in CDI there is no support of resolving dependency injection
by an external definition.

But in that case I cannot simply replace xml configuration wiring by a built-in CDI skills.
Neither @Qualifier nor @Alternative (along with @Priority) do not help because different implementations
should be supplied based on external type definition.

# Java EE solution
