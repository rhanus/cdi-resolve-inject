package com.github.rhanus.cdi.injectresolver;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(Arquillian.class)
public class GreetingBeanTest {
    @Inject
    private GreetingBean bean;

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(GreetingBean.class.getPackage())
                .addAsWebInfResource("beans.xml");
    }

    @Test
    public void injectionsResolved() {
        assertThat(bean, is(notNullValue()));

        assertThat(bean.getFirstGreeting(), instanceOf(FancyGreeting.class));
        assertEquals("Nice to meet you Kenny", bean.getFirstGreeting().greet("Kenny"));

        assertThat(bean.getSecondGreeting(), instanceOf(SimpleGreeting.class));
        assertEquals("Hello Kyle", bean.getSecondGreeting().greet("Kyle"));

        assertThat(bean.getThirdGreeting(), instanceOf(FormalGreeting.class));
        assertEquals("Good morning Stamp", bean.getThirdGreeting().greet("Stamp"));
    }
}
