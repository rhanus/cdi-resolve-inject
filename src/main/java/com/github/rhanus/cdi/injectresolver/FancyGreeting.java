package com.github.rhanus.cdi.injectresolver;


public class FancyGreeting implements Greeting {
    @Override
    public String greet(String name) {
        return "Nice to meet you " + name;
    }
}
