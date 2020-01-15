package com.example.ext;

import com.example.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.example.ext")
public class ExtConfig {

    @Bean
    public Car car() {
        return new Car();
    }
}
