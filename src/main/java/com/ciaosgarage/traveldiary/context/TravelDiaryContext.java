package com.ciaosgarage.traveldiary.context;

import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/TravelDiarySettings.properties")
@Import({DaoContext.class, ServicesContext.class})
public class TravelDiaryContext {

    @Value("${db.driverClass}")
    Class<? extends Driver> dbDriverClass;
    @Value("${db.url}")
    String dbUrl;
    @Value("${db.username}")
    String dbUsername;
    @Value("${db.password}")
    String dbPassword;


    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(dbDriverClass);
        dataSource.setUrl(dbUrl + "?characterEncoding=UTF-8");
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }


}
