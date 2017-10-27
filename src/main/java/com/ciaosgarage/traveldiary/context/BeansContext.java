package com.ciaosgarage.traveldiary.context;

import com.ciaosgarage.traveldiary.beans.systemSettings.SystemSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.ciaosgarage.traveldiary.beans")
@Import(PropertyContext.class)
public class BeansContext {


    @Autowired
    SystemSettings systemSettings;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(systemSettings.dbDriverClass);
        dataSource.setUrl(systemSettings.dbUrl + "?characterEncoding=UTF-8");
        dataSource.setUsername(systemSettings.dbUsername);
        dataSource.setPassword(systemSettings.dbPassword);
        return dataSource;
    }

}
