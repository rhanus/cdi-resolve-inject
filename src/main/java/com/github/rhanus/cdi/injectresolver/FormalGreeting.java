package com.github.rhanus.cdi.injectresolver;


public class FormalGreeting implements Greeting {
    @Override
    public String greet(String name) {
        return "Good morning " + name;
    }
}
