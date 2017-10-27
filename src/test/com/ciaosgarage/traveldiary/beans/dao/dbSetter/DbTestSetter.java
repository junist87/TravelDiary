package com.ciaosgarage.traveldiary.beans.dao.dbSetter;

import com.ciaosgarage.traveldiary.beans.dao.dao.Dao;
import com.ciaosgarage.traveldiary.domain.account.Account;
import com.ciaosgarage.traveldiary.domain.mapBoard.MapBoard;
import com.ciaosgarage.traveldiary.domain.mapPhoto.MapPhoto;
import com.ciaosgarage.traveldiary.domain.seqTable.SeqTable;

public class DbTestSetter {
    Dao dao;

    public DbTestSetter(Dao dao) {
        this.dao = dao;
    }

    public void clearAllDbTable() {
        dao.deleteAll(Account.class);
        dao.deleteAll(MapBoard.class);
        dao.deleteAll(MapPhoto.class);
        dao.deleteAll(SeqTable.class);
    }
}
