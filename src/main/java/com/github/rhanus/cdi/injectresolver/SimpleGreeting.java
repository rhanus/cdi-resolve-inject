package com.github.rhanus.cdi.injectresolver;


public class SimpleGreeting implements Greeting {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
