package com.debug.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({"com.debug.service","com.debug.aspect","com.debug.bean"})
public class AppConfig {
}
