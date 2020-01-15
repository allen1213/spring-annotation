package com.example.condition;

import com.example.bean.Book;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata  当前类的注解信息
     * @param registry  把需要的bean注册到容器中，自定义注册
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (registry.containsBeanDefinition("park")) {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Book.class);
            registry.registerBeanDefinition("book",rootBeanDefinition);
        }
    }
}
