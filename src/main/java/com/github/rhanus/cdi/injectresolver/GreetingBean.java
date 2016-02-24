package com.github.rhanus.cdi.injectresolver;

import javax.inject.Inject;


public class GreetingBean {
    @Inject
    @ResolveFrom(systemProperty = "app.first_greeting")
    private Greeting firstGreeting;

    @Inject
    @ResolveFrom(systemProperty = "app.second_greeting")
    private Greeting secondGreeting;

    @Inject
    @ResolveFrom(systemProperty = "app.third_greeting")
    private Greeting thirdGreeting;

    public Greeting getFirstGreeting() {
        return firstGreeting;
    }

    public Greeting getSecondGreeting() {
        return secondGreeting;
    }

    public Greeting getThirdGreeting() {
        return thirdGreeting;
    }
}
