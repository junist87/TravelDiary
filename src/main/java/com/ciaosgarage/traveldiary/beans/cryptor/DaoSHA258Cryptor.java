package com.ciaosgarage.traveldiary.beans.cryptor;

import com.ciaosgarage.traveldiary.beans.systemSettings.SystemSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("DaoCrpytor")
public class DaoSHA258Cryptor extends SHA258Cryptor {
    @Autowired
    SystemSettings systemSettings;

    @PostConstruct
    private void setKey() {
        super.makeKey(systemSettings.dbCryptionKey);
    }
}
