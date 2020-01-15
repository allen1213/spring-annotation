package com.example.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:/person.properties"})
public class ProfileConfig implements EmbeddedValueResolverAware {

    @Value(value = "${db.user}")
    private String user;

    @Value(value = "${db.url}")
    private String url;

    private String driver;

    private StringValueResolver resolver;

    @Bean("test")
    @Profile("test")
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setJdbcUrl(url + "test");
        dataSource.setUser(user);
        dataSource.setPassword(password);

        dataSource.setDriverClass(driver);

        return dataSource;
    }

    @Bean("dev")
    @Profile("dev")
    public DataSource dataSourceDev(@Value("${db.password}") String password) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setJdbcUrl(url  + "android");

        dataSource.setUser(user);
        dataSource.setPassword(password);

        dataSource.setDriverClass(driver);

        return dataSource;
    }

    @Bean("prod")
    @Profile("prod")
    public DataSource dataSourceProd(@Value("${db.password}") String password) throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setJdbcUrl(url  + "todo_service");

        dataSource.setUser(user);
        dataSource.setPassword(password);

        dataSource.setDriverClass(driver);

        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
        this.driver = resolver.resolveStringValue("${db.driver}");
    }
}
