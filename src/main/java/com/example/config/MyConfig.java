package com.example.config;

import com.example.bean.Color;
import com.example.bean.ColorFactoryBean;
import com.example.bean.Person;
import com.example.condition.LinuxCondition;
import com.example.condition.MyImportBeanDefinitionRegistrar;
import com.example.condition.MyImportSelector;
import com.example.condition.WindowsCondition;
import org.springframework.context.annotation.*;

@Configuration
@Conditional(WindowsCondition.class)
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MyConfig {

    @Bean("may")
    //@Scope(value = "prototype")
    //@Lazy
    public Person newInstance() {
        return new Person("MayJ",24);
    }

    @Bean("park")
    @Conditional(WindowsCondition.class)
    public Person newPerson01() {
        return new Person("Park",24);
    }

    @Bean("allen")
    @Conditional(LinuxCondition.class)
    public Person newPerson02() {
        return new Person("Allen",24);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
