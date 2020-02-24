package com.debug.test;

import com.debug.aspect.Logger;
import com.debug.bean.TestBean;
import com.debug.config.AppConfig;
import com.debug.service.IService;
import com.debug.service.IndexService;
import com.debug.service.UserService;
import org.springframework.aop.framework.AopProxy;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Test01 {

    public static void main(String[] args) throws Exception {

        //BeanFactoryPostProcessor
        //AopProxy
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        IService service = (IService) context.getBean("userService");
        service.query();

//        Properties
//        LdapContext
//        InitialLdapContext
//        SearchControls
//        NamingEnumeration<SearchResult>
    }

    @Override
    public String toString() {
        return "super.toString()";
    }
}
