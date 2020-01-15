package com.example.config;

import com.example.bean.Person;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
@ComponentScan(value = "com.example",includeFilters = {@ComponentScan.Filter(
        type = FilterType.CUSTOM,value = {MyTypeFilter.class}
)},useDefaultFilters = false)

//@PropertySource(value = {"classpath:/person.properties"})
public class AppConfig {

    @Bean(value = "person")
    Person createPerson() {
        return new Person("taylor",13);
    }

}
