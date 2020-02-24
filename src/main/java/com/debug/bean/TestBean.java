package com.debug.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Scope("prototype")
public class TestBean {

    /*@PostConstruct
    public void init() {
        System.out.println("TestBean Init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("TestBean Destroy");
    }*/

    /*public TestBean() {
        System.out.println("TestBean Constructor");
    }*/

}
