package com.debug.service;

import org.springframework.stereotype.Component;

@Component("indexService")
public class IndexService {

    public void show() throws Exception {
        System.out.println("IndexService");
        //throw new Exception();
    }

}
