package com.debug.service;

import org.springframework.stereotype.Component;

@Component("userService")
public class UserService implements IService{

    public void show() {
        System.out.println("UserService");
    }

    @Override
    public void query() {
        System.out.println("UserService : query");
    }

    @Override
    public String toString() {
        return "UserService{}";
    }
}
