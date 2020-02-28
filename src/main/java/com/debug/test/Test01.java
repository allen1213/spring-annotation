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

        //getObjectGUID("\\db\\1c\\fd\\da\\7c\\5e\\1a\\40\\a4\\e8\\15\\d3\\99\\d5\\88\\46");

    }
    
    public static void getObjectGUID(String code) {

        byte[] GUID = code.getBytes();

        StringBuffer byteGUID = new StringBuffer();
        for (int i = 0;i < GUID.length;i ++) {
            byteGUID.append("\\")
                    .append(AddLeadingZero((int)GUID[i] & 0xFF));
        }

        StringBuffer strGUID = new StringBuffer();
        strGUID.append("{")
                .append(AddLeadingZero((int)GUID[3] & 0xFF))
                .append(AddLeadingZero((int)GUID[2] & 0xFF))
                .append(AddLeadingZero((int)GUID[1] & 0xFF))
                .append(AddLeadingZero((int)GUID[0] & 0xFF))
                .append("-")
                .append(AddLeadingZero((int)GUID[5] & 0xFF))
                .append(AddLeadingZero((int)GUID[4] & 0xFF))
                .append("-")
                .append(AddLeadingZero((int)GUID[7] & 0xFF))
                .append(AddLeadingZero((int)GUID[6] & 0xFF))
                .append("-")
                .append(AddLeadingZero((int)GUID[8] & 0xFF))
                .append(AddLeadingZero((int)GUID[9] & 0xFF))
                .append("-")
                .append(AddLeadingZero((int)GUID[10] & 0xFF))
                .append(AddLeadingZero((int)GUID[11] & 0xFF))
                .append(AddLeadingZero((int)GUID[12] & 0xFF))
                .append(AddLeadingZero((int)GUID[13] & 0xFF))
                .append(AddLeadingZero((int)GUID[14] & 0xFF))
                .append(AddLeadingZero((int)GUID[15] & 0xFF))
                .append("}");

        System.out.println("GUID (String format): " + strGUID.toString());

    }

    public static String AddLeadingZero(int k) {
        return (k <= 0xF) ? "0" + Integer.toHexString(k) : Integer
                .toHexString(k);
    }

}
