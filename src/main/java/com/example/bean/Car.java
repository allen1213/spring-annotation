package com.example.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
//implements InitializingBean, DisposableBean
public class Car {
    /*@Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Car=====>AfterPropertiesSet");
    }*/

    public Car() {
        System.out.println("Car=====>Constructor");
    }

    /*@Override
    public void destroy() throws Exception {
        System.out.println("Car=====>Destroy");
    }*/
    @PostConstruct
    public void init() {
        System.out.println("Car=====>PostConstruct");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Car=====>PreDestroy");
    }
}
