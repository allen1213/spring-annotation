package com.example.config;

import com.example.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(value = {"com.example.bean"})
public class AutoWriedConfig {

    @Bean
    //@Primary
    public Person person() {
        return new Person();
    }



}
