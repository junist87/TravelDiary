package com.ciaosgarage.traveldiary.beans.systemSettings;

import com.ciaosgarage.traveldiary.beans.dao.enums.DatabaseType;
import com.mysql.jdbc.Driver;
import org.springframework.stereotype.Component;

@Component
public class SystemSettings {
    // 디비를 불러오는 드라이버
    public Class<? extends Driver> dbDriverClass;

    // 디비 url
    public String dbUrl;

    // 디비 id
    public String dbUsername;

    // 디비 password
    public String dbPassword;

    // 기본값이 존재한다
    public String dbTablePrefix;

    // 데이터베이스 종류
    public DatabaseType databaseType = DatabaseType.MYSQL;

    // Dao에 사용하는 암호화의 기본키
    public String dbCryptionKey;

    // Email 센더에 필요한 gmail 계정
    public String mailSenderEmail;

    // Email 센더에 필요한 gmail 계정 비밀번호
    public String mailSenderPw;

    public void setDatabaseType(String databaseType) {
        switch (databaseType.toLowerCase()) {
            case "mysql" :
                this.databaseType = DatabaseType.MYSQL;
        }
    }
}
