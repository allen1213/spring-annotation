package com.example.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判断是否为linux系统
public class LinuxCondition implements Condition {
    /**
     *
     * @param context       判断条件能使用的上下文环境
     * @param metadata      当前标注了@Condition的注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取到ioc容器使用的beanFactory
        //ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        //获取类加载器
        //ClassLoader classLoader = context.getClassLoader();

        //获取当前环境信息
        Environment environment = context.getEnvironment();

        String property = environment.getProperty("os.name");
        if (property.contains("Linux")) {
            return true;
        }

        //获取到bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();
        //registry.registerBeanDefinition("person",...);
        //判断是否包含person这个bean
        if (registry.containsBeanDefinition("person")) {
            return true;
        }
        return false;
    }
}
