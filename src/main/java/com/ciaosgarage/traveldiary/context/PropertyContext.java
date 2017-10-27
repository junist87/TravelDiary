package com.ciaosgarage.traveldiary.context;


import com.ciaosgarage.traveldiary.beans.systemSettings.SystemSettings;
import com.mysql.jdbc.Driver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/TravelDiarySettings.properties")
public class PropertyContext {

    @Value("${db.driverClass}")
    Class<? extends Driver> dbDriverClass;
    @Value("${db.url}")
    String dbUrl;
    @Value("${db.username}")
    String dbUsername;
    @Value("${db.password}")
    String dbPassword;
    @Value("${db.tablePrefix}")
    String dbTablePrefix;
    @Value("${db.cryptionKey}")
    String dbCryptionKey;
    @Value("${db.databaseType}")
    String dbDatabaseType;
    @Value("${mailsender.email}")
    String mailsenderEmail;
    @Value("${mailsender.password}")
    String mailsenderPassword;

    @Bean
    public SystemSettings systemSettings() {
        SystemSettings systemSettings = new SystemSettings();
        systemSettings.dbDriverClass = dbDriverClass;
        systemSettings.dbUrl = dbUrl;
        systemSettings.dbUsername = dbUsername;
        systemSettings.dbPassword = dbPassword;
        systemSettings.dbTablePrefix = dbTablePrefix;
        systemSettings.dbCryptionKey = dbCryptionKey;
        systemSettings.mailSenderEmail = mailsenderEmail;
        systemSettings.mailSenderPw = mailsenderPassword;
        systemSettings.setDatabaseType(dbDatabaseType);

        return systemSettings;
    }
}
