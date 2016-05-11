Resolve injection from a system property supplying the type information
=======================================================================

I was asked for consultancy in a project undergoing technology migration from jdk6/spring3/tomcat7
to jdk8/javaee7/wildlfy9. I was supposed to figure out how to support a specific Spring feature for which
there is no straightforward counterpart in Java EE.

## Assignment
Spring application is wired by its configuration file and the bean of interest uses three references
of the same type. The configuration file resolves which fields are bounded to which implementations.

```java
interface Greeting {...}

class GreetingManager {
    private Greeting firstGreeting;
    private Greeting secondGreeting;
    private Greeting thirdGreeting;
}
```

```xml
<beans...>
   <bean id="greetingManager" class="apackage.GreetingManager">
        <property name="firstGreeting" ref="firstGreeting"/>
        <property name="secondGreeting" ref="secondGreeting"/>
        <property name="thirdGreeting" ref="thirdGreeting"/>
    </bean>

    <bean id="firstGreeting" class="apackage.FancyGreeting"/>
    <bean id="secondGreeting" class="apackage.SimpleGreeting"/>
    <bean id="thirdGreeting" class="apackage.FormalGreeting"/>
</beans>
```

The bean of interest is the central piece of service logic. There are several project flavours
with different configurations of the bean of interest. Simply there are good reasons to support
existing implementation with minimal or better no changes.

# Technology migration design
At these days CDI is the natural choice of how to wire components together when migrating to Java EE.
Here the only acceptable code changes are supposed to be as follows:

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
and no custom configuration because in CDI there is no support of resolving particular dependency injection
by an external definition.

But in that case I cannot simply replace xml configuration wiring by a CDI feature.
Neither @Qualifier nor @Alternative (along with @Priority) do not help because different implementations
should be supplied based on external type definition.

# CDI solution

Interface javax.enterprise.inject.Instance<T> allows dynamically obtain bean instances of both required types
and annotations. Besides others it offers following method:

```java
<U extends T> Instance<U> select(Class<U> subtype, Annotation... qualifiers);
```

which matches the migration design and the required bean class should be supplied.