package com.example.config;

import com.example.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/person.properties"})
public class PropertyValueConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}
